package ru.yandex.practicum.aggregator.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class KafkaSnapshotProducer {

    private final Producer<String, Object> producer;

    public KafkaSnapshotProducer(Properties producerProperties) {
        this.producer = new KafkaProducer<>(producerProperties);
    }

    public void sendSnapshot(String hubId, Object snapshot) {
        producer.send(new ProducerRecord<>("telemetry.snapshots.v1", hubId, snapshot));
    }

    public void flush() {
        producer.flush();
    }

    public void close() {
        producer.close();
    }
}