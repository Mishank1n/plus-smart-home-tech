package ru.yandex.practicum.aggregator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.aggregator.producer.KafkaSnapshotProducer;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

import java.time.Duration;
import java.util.Properties;

@Component
@RequiredArgsConstructor
@Slf4j
public class AggregationStarter {

    private final Properties consumerProperties;
    private final KafkaSnapshotProducer snapshotProducer;
    private final SnapshotService snapshotService;

    public void start() {
        log.info("Aggregator запущен");
        try (Consumer<String, SensorEventAvro> consumer = new KafkaConsumer<>(consumerProperties)) {
            consumer.subscribe(java.util.Collections.singletonList("telemetry.sensors.v1"));

            while (true) {
                ConsumerRecords<String, SensorEventAvro> records = consumer.poll(Duration.ofMillis(100));
                for (var record : records) {
                    SensorEventAvro event = record.value();
                    if (event == null) continue;

                    snapshotService.updateState(event)
                            .ifPresent(snapshot -> {
                                snapshotProducer.sendSnapshot(snapshot.getHubId(), snapshot);
                                log.debug("Отправлен снапшот для hubId={}", snapshot.getHubId());
                            });
                }
                consumer.commitSync();
            }
        } catch (WakeupException ignored) {
            log.info("Consumer остановлен");
        } catch (Exception e) {
            log.error("Ошибка в цикле агрегации", e);
        } finally {
            snapshotProducer.flush();
            log.info("Aggregator остановлен");
        }
    }
}