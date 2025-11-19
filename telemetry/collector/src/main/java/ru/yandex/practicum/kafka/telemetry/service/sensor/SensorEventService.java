package ru.yandex.practicum.kafka.telemetry.service.sensor;

import ru.yandex.practicum.kafka.telemetry.model.sensor.SensorEvent;

public interface SensorEventService {

    void processEvent(SensorEvent event);
}
