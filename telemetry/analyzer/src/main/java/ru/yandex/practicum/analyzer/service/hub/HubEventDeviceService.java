package ru.yandex.practicum.analyzer.service.hub;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.analyzer.exception.NotFoundException;
import ru.yandex.practicum.analyzer.model.Sensor;
import ru.yandex.practicum.analyzer.repository.SensorRepository;

@Service
@RequiredArgsConstructor
public class HubEventDeviceService {

    private final SensorRepository sensorRepository;

    @Transactional
    public void save(String sensorId, String hubId) {
        Sensor sensor = Sensor.builder()
                .id(sensorId)
                .hubId(hubId)
                .build();
        sensorRepository.save(sensor);
    }

    @Transactional
    public void remove(String sensorId, String hubId) {
        Sensor sensor = sensorRepository.findByIdAndHubId(sensorId, hubId)
                .orElseThrow(() -> new NotFoundException(String.format("Сенсор %s не найден", sensorId)));
        sensorRepository.delete(sensor);
    }
}
