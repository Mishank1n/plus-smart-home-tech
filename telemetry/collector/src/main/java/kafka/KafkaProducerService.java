package kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.hub.HubEvent;
import model.mapper.HubEventAvroMapper;
import model.mapper.SensorEventAvroMapper;
import model.sensor.SensorEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;


@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String SENSORS_TOPIC = "telemetry.sensors.v1";
    private static final String HUBS_TOPIC = "telemetry.hubs.v1";

    public void sendSensorEvent(SensorEvent sensorEvent) {
        try {
            SensorEventAvro avroEvent = SensorEventAvroMapper.toAvro(sensorEvent);

            kafkaTemplate.send(SENSORS_TOPIC, sensorEvent.getId(), avroEvent);
            log.info("Sent sensor event to topic '{}': {}", SENSORS_TOPIC, sensorEvent);
        } catch (Exception e) {
            log.error("Error sending sensor event to Kafka: {}", e.getMessage(), e);

            throw e;
        }
    }

    public void sendHubEvent(HubEvent hubEvent) {
        try {
            HubEventAvro avroEvent = HubEventAvroMapper.toAvro(hubEvent);

            kafkaTemplate.send(HUBS_TOPIC, hubEvent.getHubId(), avroEvent);
            log.info("Sent hub event to topic '{}': {}", HUBS_TOPIC, hubEvent);
        } catch (Exception e) {
            log.error("Error sending hub event to Kafka: {}", e.getMessage(), e);

            throw e;
        }
    }
}