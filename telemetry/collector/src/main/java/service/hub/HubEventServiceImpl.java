package service.hub;

import kafka.KafkaTelemetryProducer;
import lombok.AllArgsConstructor;
import model.hub.HubEvent;
import model.mapper.HubEventAvroMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HubEventServiceImpl implements HubEventService {

    @Autowired
    private final KafkaTelemetryProducer kafkaTelemetryProducer;

    @Override
    public void processEvent(HubEvent event) {
        kafkaTelemetryProducer.sendHubEvent(HubEventAvroMapper.toAvro(event));
    }
}
