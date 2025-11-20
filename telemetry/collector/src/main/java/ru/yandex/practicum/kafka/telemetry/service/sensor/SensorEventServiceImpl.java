package ru.yandex.practicum.kafka.telemetry.service.sensor;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.kafka.KafkaProducerService;

@Service
@AllArgsConstructor
public class SensorEventServiceImpl implements SensorEventService {

    @Autowired
    private final KafkaProducerService kafkaProducerService;

    @Override
    public void processEvent(SensorEventProto event) {
        kafkaProducerService.sendSensorEvent(event);
    }
}
