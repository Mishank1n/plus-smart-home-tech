package ru.yandex.practicum.kafka.telemetry.service.hub;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.kafka.KafkaProducerService;

@Service
@AllArgsConstructor
public class HubEventServiceImpl implements HubEventService {

    @Autowired
    private final KafkaProducerService kafkaProducerService;

    @Override
    public void processEvent(HubEventProto event) {
        kafkaProducerService.sendHubEvent(event);
    }
}
