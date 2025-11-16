package service.sensor;

import kafka.KafkaTelemetryProducer;
import lombok.AllArgsConstructor;
import model.mapper.SensorEventAvroMapper;
import model.sensor.SensorEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SensorEventServiceImpl implements SensorEventService {

    @Autowired
    private final KafkaTelemetryProducer kafkaTelemetryProducer;

    @Override
    public void processEvent(SensorEvent event) {
        kafkaTelemetryProducer.sendSensorEvent(SensorEventAvroMapper.toAvro(event));
    }
}
