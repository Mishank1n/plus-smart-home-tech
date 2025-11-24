package ru.yandex.practicum.analyzer.model;


import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScenarioConditionId {

    Long scenarioId;

    String sensorId;

    Long conditionId;
}
