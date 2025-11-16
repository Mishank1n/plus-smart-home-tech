package controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import model.hub.HubEvent;
import model.sensor.SensorEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import service.hub.HubEventService;
import service.sensor.SensorEventService;

@RestController
@RequestMapping(path = "/events")
@AllArgsConstructor
public class EventController {

    @Autowired
    private final HubEventService hubEventService;

    @Autowired
    private final SensorEventService sensorEventService;

    @PostMapping("/hubs")
    @ResponseStatus(HttpStatus.OK)
    public void postHubEvent(@RequestBody @Valid HubEvent hubEvent) {

    }

    @PostMapping("/sensors")
    @ResponseStatus(HttpStatus.OK)
    public void postSensorEvent(@RequestBody @Valid SensorEvent sensorEvent) {

    }
}
