package ru.yandex.practicum.aggregator.service;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class SnapshotService {

    private final Map<String, SensorsSnapshotAvro> snapshots = new HashMap<>();

    public Optional<SensorsSnapshotAvro> updateState(SensorEventAvro event) {
        String hubId = event.getHubId();
        String sensorId = event.getId();

        SensorsSnapshotAvro snapshot = snapshots.computeIfAbsent(hubId, id -> {
            SensorsSnapshotAvro s = new SensorsSnapshotAvro();
            s.setHubId(id);
            s.setSensorsState(new HashMap<>());
            s.setTimestamp(event.getTimestamp());
            return s;
        });

        Map<String, SensorStateAvro> states = snapshot.getSensorsState();
        SensorStateAvro oldState = states.get(sensorId);

        // Игнорируем устаревшие или дублирующие события
        if (oldState != null &&
                (oldState.getTimestamp().isAfter(event.getTimestamp()) ||
                        oldState.getData().equals(event.getPayload()))) {
            return Optional.empty();
        }

        // Обновляем состояние
        SensorStateAvro newState = new SensorStateAvro();
        newState.setTimestamp(event.getTimestamp());
        newState.setData(event.getPayload());

        states.put(sensorId, newState);
        snapshot.setSensorsState(states);
        snapshot.setTimestamp(event.getTimestamp());

        return Optional.of(snapshot);
    }
}