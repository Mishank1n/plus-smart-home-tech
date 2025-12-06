package ru.yandex.practicum.kafka.telemetry.service.sensor;

import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

public interface SensorEventService {

    void processEvent(SensorEventProto event);
}
