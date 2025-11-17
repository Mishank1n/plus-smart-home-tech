package service.hub;


import kafka.KafkaProducerService;
import lombok.AllArgsConstructor;
import model.hub.HubEvent;
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
