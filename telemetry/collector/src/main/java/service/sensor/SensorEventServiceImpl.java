package service.sensor;


import kafka.KafkaProducerService;
import lombok.AllArgsConstructor;
import model.sensor.SensorEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SensorEventServiceImpl implements SensorEventService {

    @Autowired
    private final KafkaProducerService kafkaProducerService;

    @Override
    public void processEvent(SensorEvent event) {
        kafkaProducerService.sendSensorEvent(event);
    }
}
