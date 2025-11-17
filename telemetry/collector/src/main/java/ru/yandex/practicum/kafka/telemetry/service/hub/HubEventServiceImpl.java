package ru.yandex.practicum.kafka.telemetry.service.hub;


import ru.yandex.practicum.kafka.telemetry.kafka.KafkaProducerService;
import lombok.AllArgsConstructor;
import ru.yandex.practicum.kafka.telemetry.model.hub.HubEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HubEventServiceImpl implements HubEventService {

    @Autowired
    private final KafkaProducerService kafkaProducerService;

    @Override
    public void processEvent(HubEvent event) {
        kafkaProducerService.sendHubEvent(event);
    }
}
