package ru.yandex.practicum.analyzer.service.hub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.analyzer.service.scenario.HubEventScenarioService;
import ru.yandex.practicum.kafka.telemetry.event.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class HubEventService {

    private final HubEventDeviceService hubEventDeviceService;
    private final HubEventScenarioService hubEventScenarioService;

    public void saveHubEvent(ConsumerRecords<String, HubEventAvro> hubEventList) {
        hubEventList.forEach(record -> {
            HubEventAvro event = record.value();
            Object payloadClass = event.getPayload().getClass();

            if (payloadClass.equals(DeviceAddedEventAvro.class)) {
                DeviceAddedEventAvro deviceAdded = (DeviceAddedEventAvro) event.getPayload();
                hubEventDeviceService.save(deviceAdded.getId(), event.getHubId());
                log.info("Сенсор {} добавлен в хаб {}!", deviceAdded.getId(), event.getHubId());
            }

            if (payloadClass.equals(ScenarioAddedEventAvro.class)) {
                ScenarioAddedEventAvro scenarioAdded = (ScenarioAddedEventAvro) event.getPayload();
                hubEventScenarioService.save(event, scenarioAdded);
                log.info("Сценарий {} добавлен!", scenarioAdded.getName());
            }

            if (payloadClass.equals(DeviceRemovedEventAvro.class)) {
                DeviceRemovedEventAvro deviceRemoved = (DeviceRemovedEventAvro) event.getPayload();
                hubEventDeviceService.remove(deviceRemoved.getId(), event.getHubId());
                log.info("Сенсор {} удален!", deviceRemoved.getId());
            }

            if (payloadClass.equals(ScenarioRemovedEventAvro.class)) {
                ScenarioRemovedEventAvro scenarioRemoved = (ScenarioRemovedEventAvro) event.getPayload();
                hubEventScenarioService.remove(scenarioRemoved.getName(), event.getHubId());
                log.info("Сценарий {} удален!", scenarioRemoved.getName());
            }
        });
    }
}