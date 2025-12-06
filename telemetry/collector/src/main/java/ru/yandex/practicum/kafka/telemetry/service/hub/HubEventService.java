package ru.yandex.practicum.kafka.telemetry.service.hub;

import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;

public interface HubEventService {
    void processEvent(HubEventProto event);
}
