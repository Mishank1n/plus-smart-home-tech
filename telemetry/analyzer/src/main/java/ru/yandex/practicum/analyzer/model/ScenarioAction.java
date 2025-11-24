package ru.yandex.practicum.analyzer.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "scenario_actions", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScenarioAction {

    @EmbeddedId
    ScenarioActionId id;

    @ManyToOne
    @JoinColumn(name = "scenario_id")
    @MapsId("scenarioId")
    Scenario scenario;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    @MapsId("sensorId")
    Sensor sensor;

    @ManyToOne
    @JoinColumn(name = "action_id")
    @MapsId("actionId")
    Action action;
}
