package controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.hub.HubEvent;
import model.sensor.SensorEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.hub.HubEventService;
import service.sensor.SensorEventService;

@Slf4j
@RestController
@RequestMapping("/events") // <-- Изменен на /events, как ожидает тест
@RequiredArgsConstructor
public class EventController {

    private final HubEventService hubEventService;
    private final SensorEventService sensorEventService;

    @PostMapping("/sensors") // Теперь будет /events/sensors
    public ResponseEntity<Void> collectSensorEvent(@Valid @RequestBody SensorEvent event) {
        log.info("Received sensor event: {}", event);
        sensorEventService.processEvent(event);
        return ResponseEntity.ok().build(); // 200 OK
    }

    @PostMapping("/hubs") // Теперь будет /events/hubs
    public ResponseEntity<Void> collectHubEvent(@Valid @RequestBody HubEvent event) {
        log.info("Received hub event: {}", event);
        hubEventService.processEvent(event);
        return ResponseEntity.ok().build(); // 200 OK
    }
}