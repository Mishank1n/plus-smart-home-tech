package ru.yandex.practicum.analyzer.service.scenario;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.analyzer.exception.NotFoundException;
import ru.yandex.practicum.analyzer.model.*;
import ru.yandex.practicum.analyzer.repository.*;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HubEventScenarioService {

    ScenarioConditionRepository scenarioConditionRepository;
    ScenarioActionRepository scenarioActionRepository;
    ScenarioRepository scenarioRepository;
    ConditionRepository conditionRepository;
    ActionRepository actionRepository;
    SensorRepository sensorRepository;


    @Transactional
    public void save(HubEventAvro event, ScenarioAddedEventAvro added) {
        Scenario newScenario = Scenario.builder()
                .hubId(event.getHubId())
                .name(added.getName())
                .build();

        Scenario savedScenario = scenarioRepository.save(newScenario);

        added.getConditions().forEach(condition -> {
            Integer value = null;
            if (condition.getValue() instanceof Boolean boolVal) {
                value = boolVal ? 1 : 0;
            } else if (condition.getValue() instanceof Integer intVal) {
                value = intVal;
            }

            Condition newCondition = Condition.builder()
                    .type(condition.getType().toString())
                    .operation(condition.getOperation().toString())
                    .value(value)
                    .build();

            Condition savedCondition = conditionRepository.save(newCondition);

            Sensor sensor = sensorRepository.findByIdAndHubId(condition.getSensorId(), event.getHubId())
                    .orElseThrow(() -> new NotFoundException(String.format("Сенсор %s не найден", condition.getSensorId())));

            ScenarioConditionId sensorId = ScenarioConditionId.builder()
                    .scenarioId(savedScenario.getId())
                    .sensorId(sensor.getId())
                    .conditionId(savedCondition.getId())
                    .build();

            ScenarioCondition scenarioCondition = ScenarioCondition.builder()
                    .id(sensorId)
                    .scenario(savedScenario)
                    .sensor(sensor)
                    .condition(savedCondition)
                    .build();

            scenarioConditionRepository.save(scenarioCondition);
        });

        added.getActions().forEach(action -> {
            Action newAction = Action.builder()
                    .type(action.getType().toString())
                    .value(action.getValue())
                    .build();

            Action saveAction = actionRepository.save(newAction);

            Sensor sensor = sensorRepository.findByIdAndHubId(action.getSensorId(), event.getHubId())
                    .orElseThrow(() -> new NotFoundException(String.format("Сенсор %s не найден", action.getSensorId())));

            ScenarioActionId actionId = ScenarioActionId.builder()
                    .scenarioId(savedScenario.getId())
                    .sensorId(sensor.getId())
                    .actionId(saveAction.getId())
                    .build();

            ScenarioAction scenarioAction = ScenarioAction.builder()
                    .id(actionId)
                    .scenario(savedScenario)
                    .sensor(sensor)
                    .action(saveAction)
                    .build();

            scenarioActionRepository.save(scenarioAction);
        });
    }

    @Transactional
    public void remove(String name, String hubId) {
        Scenario scenario = scenarioRepository.findByHubIdAndName(name, hubId)
                .orElseThrow(() -> new NotFoundException(String.format("Сценарий %s не найден", name)));
        scenarioRepository.delete(scenario);
    }
}
