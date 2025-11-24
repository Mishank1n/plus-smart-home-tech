package ru.yandex.practicum.analyzer.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.errors.WakeupException;

import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Component;

import ru.yandex.practicum.analyzer.grpc.AnalyzerClient;

import ru.yandex.practicum.analyzer.service.scenario.ScenarioProcessor;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SensorsSnapshotProcessor {

    private final ConsumerFactory<String, SensorsSnapshotAvro> shapshotConsumerFactory;
    private final ScenarioProcessor scenarioProcessor;
    private final AnalyzerClient service;


    public void start() {
        try (Consumer<String, SensorsSnapshotAvro> sensorsSnapshotConsumer = shapshotConsumerFactory.createConsumer()) {
            sensorsSnapshotConsumer.subscribe(List.of("telemetry.snapshots.v1"));

            while(true) {
                var snapshotRecords = sensorsSnapshotConsumer.poll(Duration.ofSeconds(3));
                if (snapshotRecords.count() > 0) {
                    log.info("Получено {} записей", snapshotRecords.count());

                    List<SensorsSnapshotAvro> snapshotList = new ArrayList<>();
                    snapshotRecords.forEach(record -> snapshotList.add(record.value()));
                    snapshotList.forEach(snapshot -> {
                        List<DeviceActionRequest> actions = scenarioProcessor.evaluateScenarios(snapshot);
                        actions.forEach(service::sendDeviceActions);
                    });
                    snapshotList.clear();
                }

                sensorsSnapshotConsumer.commitSync();
            }
        } catch (WakeupException ignored) {
        } catch (Exception e) {
            log.error("Ошибка при агрегации событий от датчиков", e);
        }
    }
}