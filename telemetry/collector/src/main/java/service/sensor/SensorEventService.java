package service.sensor;

import model.sensor.SensorEvent;

public interface SensorEventService {

    void processEvent(SensorEvent event);
}
