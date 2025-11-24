package ru.yandex.practicum.analyzer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "scenario_actions", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScenarioAction {

    @EmbeddedId
    ScenarioActionId id;

    @ManyToOne
    @JoinColumn(name = "scenario_id")
    @MapsId("scenarioId")
    private Scenario scenario;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    @MapsId("sensorId")
    private Sensor sensor;

    @ManyToOne
    @JoinColumn(name = "action_id")
    @MapsId("actionId")
    private Action action;
}
