package ru.yandex.practicum.kafka.telemetry.model.mapper;

import ru.yandex.practicum.grpc.telemetry.event.*;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.time.Instant;

public class SensorEventProtoToAvroMapper {

    public static SensorEventAvro toAvro(SensorEventProto sensorEvent) {
        SensorEventAvro.Builder builder = SensorEventAvro.newBuilder()
                .setId(sensorEvent.getId())
                .setTimestamp(Instant.ofEpochSecond(sensorEvent.getTimestamp().getSeconds(), sensorEvent.getTimestamp().getNanos()))
                .setHubId(sensorEvent.getHubId());
        switch (sensorEvent.getPayloadCase()) {
            case LIGHT_SENSOR -> {
                LightSensorProto lightSensorEvent = sensorEvent.getLightSensor();
                builder.setPayload(
                        LightSensorAvro.newBuilder()
                                .setLinkQuality(lightSensorEvent.getLinkQuality())
                                .setLuminosity(lightSensorEvent.getLuminosity())
                                .build()
                );
                break;
            }
            case MOTION_SENSOR -> {
                MotionSensorProto motionSensorEvent = sensorEvent.getMotionSensor();
                builder.setPayload(
                        MotionSensorAvro.newBuilder()
                                .setLinkQuality(motionSensorEvent.getLinkQuality())
                                .setMotion(motionSensorEvent.getMotion())
                                .setVoltage(motionSensorEvent.getVoltage())
                                .build()
                );
                break;
            }
            case SWITCH_SENSOR -> {
                SwitchSensorProto switchSensorEvent = sensorEvent.getSwitchSensor();
                builder.setPayload(
                        SwitchSensorAvro.newBuilder()
                                .setState(switchSensorEvent.getState())
                                .build()
                );
                break;
            }
            case CLIMATE_SENSOR -> {
                ClimateSensorProto climateSensorEvent = sensorEvent.getClimateSensor();
                builder.setPayload(
                        ClimateSensorAvro.newBuilder()
                                .setCo2Level(climateSensorEvent.getCo2Level())
                                .setHumidity(climateSensorEvent.getHumidity())
                                .setTemperatureC(climateSensorEvent.getTemperatureC())
                                .build()
                );
                break;
            }
            case TEMPERATURE_SENSOR -> {
                TemperatureSensorProto temperatureSensorEvent = sensorEvent.getTemperatureSensor();
                builder.setPayload(
                        TemperatureSensorAvro.newBuilder()
                                .setTemperatureC(temperatureSensorEvent.getTemperatureC())
                                .setTemperatureF(temperatureSensorEvent.getTemperatureF())
                                .build()
                );
                break;
            }
        }
        return builder.build();
    }
}