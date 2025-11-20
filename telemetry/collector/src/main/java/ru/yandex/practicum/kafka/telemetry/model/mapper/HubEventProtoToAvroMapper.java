package ru.yandex.practicum.kafka.telemetry.model.mapper;

import ru.yandex.practicum.grpc.telemetry.event.*;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.time.Instant;

public class HubEventProtoToAvroMapper {

    public static HubEventAvro toAvro(HubEventProto hubEventProto) {
        HubEventAvro.Builder builder = HubEventAvro.newBuilder()
                .setHubId(hubEventProto.getHubId())
                .setTimestamp(Instant.ofEpochSecond(hubEventProto.getTimestamp().getSeconds(), hubEventProto.getTimestamp().getNanos()));
        switch (hubEventProto.getPayloadCase()) {
            case DEVICE_ADDED -> {
                DeviceAddedEventProto deviceAddedEvent = hubEventProto.getDeviceAdded();
                builder.setPayload(
                        DeviceAddedEventAvro.newBuilder()
                                .setId(deviceAddedEvent.getId())
                                .setType(DeviceTypeAvro.valueOf(deviceAddedEvent.getType().name()))
                                .build()
                );
                break;
            }
            case DEVICE_REMOVED -> {
                DeviceRemovedEventProto deviceRemovedEvent = hubEventProto.getDeviceRemoved();
                builder.setPayload(
                        DeviceRemovedEventAvro.newBuilder()
                                .setId(deviceRemovedEvent.getId())
                                .build()
                );
                break;
            }
            case SCENARIO_ADDED -> {
                ScenarioAddedEventProto scenarioAddedEvent = hubEventProto.getScenarioAdded();
                builder.setPayload(
                        ScenarioAddedEventAvro.newBuilder()
                                .setName(scenarioAddedEvent.getName())
                                .setConditions(scenarioAddedEvent.getConditionList().stream()
                                        .map(scenarioCondition -> {
                                                    ScenarioConditionAvro.Builder scenarioConditionAvro = ScenarioConditionAvro.newBuilder();

                                                    scenarioConditionAvro.setType(ConditionTypeAvro.valueOf(scenarioCondition.getType().name()))
                                                            .setOperation(ConditionOperationAvro.valueOf(scenarioCondition.getOperation().name()))
                                                            .setSensorId(scenarioCondition.getSensorId());
                                                    switch (scenarioCondition.getValueCase()) {
                                                        case BOOL_VALUE ->
                                                                scenarioConditionAvro.setValue(scenarioCondition.getBoolValue());

                                                        case INT_VALUE ->
                                                                scenarioConditionAvro.setValue(scenarioCondition.getIntValue());
                                                        default -> scenarioConditionAvro.setValue((Object) null);
                                                    }
                                                    return scenarioConditionAvro.build();
                                                }
                                        ).toList()
                                )
                                .setActions(scenarioAddedEvent.getActionList().stream()
                                        .map(deviceAction ->
                                                DeviceActionAvro.newBuilder()
                                                        .setSensorId(deviceAction.getSensorId())
                                                        .setType(ActionTypeAvro.valueOf(deviceAction.getType().name()))
                                                        .setValue(deviceAction.getValue()).build()

                                        ).toList()
                                ).build()
                );
                break;
            }
            case SCENARIO_REMOVED -> {
                ScenarioRemovedEventProto scenarioRemovedEvent = hubEventProto.getScenarioRemoved();
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
