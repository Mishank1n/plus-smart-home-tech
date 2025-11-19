package ru.yandex.practicum.kafka.telemetry.service.hub;

import ru.yandex.practicum.kafka.telemetry.model.hub.HubEvent;

public interface HubEventService {
    void processEvent(HubEvent event);
}
