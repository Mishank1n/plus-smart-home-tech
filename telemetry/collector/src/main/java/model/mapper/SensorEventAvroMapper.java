package model.mapper;

import model.sensor.*;
import ru.yandex.practicum.kafka.telemetry.event.*;

public class SensorEventAvroMapper {

    public static SensorEventAvro toAvro(SensorEvent sensorEvent) {
        SensorEventAvro.Builder builder = SensorEventAvro.newBuilder()
                .setId(sensorEvent.getId())
                .setTimestamp(sensorEvent.getTimestamp())
                .setHubId(sensorEvent.getHubId());
        switch (sensorEvent.getType()) {
            case LIGHT_SENSOR_EVENT -> {
                LightSensorEvent lightSensorEvent = (LightSensorEvent) sensorEvent;
                builder.setPayload(
                        LightSensorAvro.newBuilder()
                                .setLinkQuality(lightSensorEvent.getLinkQuality())
                                .setLuminosity(lightSensorEvent.getLuminosity())
                                .build()
                );
                break;
            }
            case MOTION_SENSOR_EVENT -> {
                MotionSensorEvent motionSensorEvent = (MotionSensorEvent) sensorEvent;
                builder.setPayload(
                        MotionSensorAvro.newBuilder()
                                .setLinkQuality(motionSensorEvent.getLinkQuality())
                                .setMotion(motionSensorEvent.isMotion())
                                .setVoltage(motionSensorEvent.getVoltage())
                                .build()
                );
                break;
            }
            case SWITCH_SENSOR_EVENT -> {
                SwitchSensorEvent switchSensorEvent = (SwitchSensorEvent) sensorEvent;
                builder.setPayload(
                        SwitchSensorAvro.newBuilder()
                                .setState(switchSensorEvent.isState())
                                .build()
                );
                break;
            }
            case CLIMATE_SENSOR_EVENT -> {
                ClimateSensorEvent climateSensorEvent = (ClimateSensorEvent) sensorEvent;
                builder.setPayload(
                        ClimateSensorAvro.newBuilder()
                                .setCo2Level(climateSensorEvent.getCo2Level())
                                .setHumidity(climateSensorEvent.getHumidity())
                                .setTemperatureC(climateSensorEvent.getTemperatureC())
                                .build()
                );
                break;
            }
            case TEMPERATURE_SENSOR_EVENT -> {
                TemperatureSensorEvent temperatureSensorEvent = (TemperatureSensorEvent) sensorEvent;
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