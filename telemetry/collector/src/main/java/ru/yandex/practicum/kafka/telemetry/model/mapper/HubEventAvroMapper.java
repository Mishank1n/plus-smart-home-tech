package ru.yandex.practicum.kafka.telemetry.model.mapper;

import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.kafka.telemetry.model.hub.*;

public class HubEventAvroMapper {

    public static HubEventAvro toAvro(HubEvent hubEvent) {
        HubEventAvro.Builder builder = HubEventAvro.newBuilder()
                .setHubId(hubEvent.getHubId())
                .setTimestamp(hubEvent.getTimestamp());
        switch (hubEvent.getType()) {
            case DEVICE_ADDED -> {
                DeviceAddedEvent deviceAddedEvent = (DeviceAddedEvent) hubEvent;
                builder.setPayload(
                        DeviceAddedEventAvro.newBuilder()
                                .setId(deviceAddedEvent.getId())
                                .setType(DeviceTypeAvro.valueOf(deviceAddedEvent.getDeviceType().name()))
                                .build()
                );
                break;
            }
            case DEVICE_REMOVED -> {
                DeviceRemovedEvent deviceRemovedEvent = (DeviceRemovedEvent) hubEvent;
                builder.setPayload(
                        DeviceRemovedEventAvro.newBuilder()
                                .setId(deviceRemovedEvent.getId())
                                .build()
                );
                break;
            }
            case SCENARIO_ADDED -> {
                ScenarioAddedEvent scenarioAddedEvent = (ScenarioAddedEvent) hubEvent;
                builder.setPayload(
                        ScenarioAddedEventAvro.newBuilder()
                                .setName(scenarioAddedEvent.getName())
                                .setConditions(scenarioAddedEvent.getConditions().stream()
                                        .map(scenarioCondition -> {
                                                    ScenarioConditionAvro.Builder scenarioConditionAvro = ScenarioConditionAvro.newBuilder();

                                                    scenarioConditionAvro.setType(ConditionTypeAvro.valueOf(scenarioCondition.getType().name()))
                                                            .setOperation(ConditionOperationAvro.valueOf(scenarioCondition.getOperation().name()))
                                                            .setSensorId(scenarioCondition.getSensorId());
                                                    Object value = scenarioCondition.getValue();
                                                    if (value instanceof Integer) {
                                                        scenarioConditionAvro.setValue((Integer) value);
                                                    } else if (value instanceof Boolean) {
                                                        scenarioConditionAvro.setValue((Boolean) value);
                                                    } else {
                                                        scenarioConditionAvro.setValue((Object) null);
                                                    }
                                                    return scenarioConditionAvro.build();
                                                }
                                        ).toList()
                                )
                                .setActions(scenarioAddedEvent.getActions().stream()
                                        .map(deviceAction ->
                                                DeviceActionAvro.newBuilder()
                                                        .setSensorId(deviceAction.getSensorId())
                                                        .setType(ActionTypeAvro.valueOf(deviceAction.getType().name()))
                                                        .setValue(deviceAction.getValue() != null ? deviceAction.getValue() : null)
                                                        .build()
                                        ).toList()
                                ).build()
                );
                break;
            }
            case SCENARIO_REMOVED -> {
                ScenarioRemovedEvent scenarioRemovedEvent = (ScenarioRemovedEvent) hubEvent;
                builder.setPayload(
                        ScenarioRemovedEventAvro.newBuilder()
                                .setName(scenarioRemovedEvent.getName())
                                .build()
                );
                break;
            }
        }
        return builder.build();
    }
}
