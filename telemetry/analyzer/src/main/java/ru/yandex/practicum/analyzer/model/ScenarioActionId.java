package ru.yandex.practicum.analyzer.model;

import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ScenarioActionId {

    Long scenarioId;

    String sensorId;

    Long actionId;
}
