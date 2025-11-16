package kafka;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.serializer.GeneralAvroSerializer;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

import java.util.Properties;

@Service
public class KafkaTelemetryProducer {

    private final KafkaProducer<Object, SpecificRecordBase> producer;
    private final String BOOTSTRAP_SERVER = "localhost:9092";

    public KafkaTelemetryProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVER);
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", GeneralAvroSerializer.class.getName());

        this.producer = new KafkaProducer<>(props);
    }

    public void sendSensorEvent(SensorEventAvro avro) {
        producer.send(
                new ProducerRecord<>("telemetry.sensors.v1", avro.getId(), avro)
        );
    }

    public void sendHubEvent(HubEventAvro avro) {
        producer.send(
                new ProducerRecord<>("telemetry.hubs.v1", avro.getHubId(), avro)
        );
    }
}
