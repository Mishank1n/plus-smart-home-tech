package ru.yandex.practicum.analyzer.service.scenario;

import com.google.protobuf.util.Timestamps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.analyzer.exception.NotFoundException;
import ru.yandex.practicum.analyzer.model.Scenario;
import ru.yandex.practicum.analyzer.model.ScenarioAction;
import ru.yandex.practicum.analyzer.model.ScenarioCondition;
import ru.yandex.practicum.analyzer.repository.ScenarioActionRepository;
import ru.yandex.practicum.analyzer.repository.ScenarioConditionRepository;
import ru.yandex.practicum.analyzer.repository.ScenarioRepository;
import ru.yandex.practicum.analyzer.repository.SensorRepository;
import ru.yandex.practicum.grpc.telemetry.event.*;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ScenarioProcessor {

    private final ScenarioActionRepository scenarioActionRepository;
    private final ScenarioRepository scenarioRepository;
    private final SensorRepository sensorRepository;
    private final ScenarioConditionRepository scenarioConditionRepository;

    public List<DeviceActionRequest> evaluateScenarios(SensorsSnapshotAvro snapshot) {

        List<DeviceActionRequest> result = new ArrayList<>();

        List<Scenario> scenarioList = scenarioRepository.findByHubId(snapshot.getHubId());
        if (scenarioList.isEmpty()) {
            return result;
        }

        List<Long> scenarioIds = scenarioList.stream()
                .map(Scenario::getId)
                .toList();

        List<ScenarioCondition> allConditions =
                scenarioConditionRepository.findAllByScenarioIdIn(scenarioIds);
        List<ScenarioAction> allActions =
                scenarioActionRepository.findAllByScenarioIdIn(scenarioIds);

        Map<Long, List<ScenarioCondition>> conditionsByScenario = allConditions.stream()
                .collect(Collectors.groupingBy(sc -> sc.getScenario().getId()));
        Map<Long, List<ScenarioAction>> actionsByScenario = allActions.stream()
                .collect(Collectors.groupingBy(sa -> sa.getScenario().getId()));

        for (Scenario scenario : scenarioList) {
            List<ScenarioCondition> scenarioConditions =
                    conditionsByScenario.getOrDefault(scenario.getId(), List.of());

            boolean allConditionsTrue = scenarioConditions.stream()
                    .allMatch(condition -> checkCondition(condition, snapshot, snapshot.getHubId()));

            if (allConditionsTrue) {
                List<ScenarioAction> actions =
                        actionsByScenario.getOrDefault(scenario.getId(), List.of());

                for (ScenarioAction action : actions) {
                    DeviceActionProto deviceActionProto = DeviceActionProto.newBuilder()
                            .setSensorId(action.getSensor().getId())
                            .setType(ActionTypeProto.valueOf(action.getAction().getType()))
                            .setValue(action.getAction().getValue())
                            .build();

                    DeviceActionRequest request = DeviceActionRequest.newBuilder()
                            .setHubId(snapshot.getHubId())
                            .setScenarioName(action.getScenario().getName())
                            .setAction(deviceActionProto)
                            .setTimestamp(Timestamps.fromMillis(System.currentTimeMillis()))
                            .build();

                    result.add(request);
                }
            }
        }

        return result;
    }


    private boolean checkCondition(ScenarioCondition condition, SensorsSnapshotAvro snapshot, String hubId) {

        String sensorId = condition.getSensor().getId();

        SensorStateAvro state = snapshot.getSensorsState().get(sensorId);
        if (state == null || state.getData() == null) {
            return true;
        }

        sensorRepository.findByIdAndHubId(sensorId, hubId)
                .orElseThrow(() -> new NotFoundException(String.format("Датчик %s не найден", sensorId )));

        ConditionOperationAvro operation = ConditionOperationAvro.valueOf(condition.getCondition().getOperation());

        ConditionTypeAvro deviceType = ConditionTypeAvro.valueOf(condition.getCondition().getType());

        return switch (deviceType) {
            case MOTION -> {
                MotionSensorAvro data = (MotionSensorAvro) state.getData();
                int actual = data.getMotion() ? 1 : 0;
                yield checkOperation(operation, actual, condition.getCondition().getValue());
            }
            case LUMINOSITY -> {
                LightSensorAvro data = (LightSensorAvro) state.getData();
                int actual = data.getLuminosity();
                yield checkOperation(operation, actual, condition.getCondition().getValue());
            }
            case TEMPERATURE -> {
                ClimateSensorAvro data = (ClimateSensorAvro) state.getData();
                int actual = data.getTemperatureC();
                yield checkOperation(operation, actual, condition.getCondition().getValue());
            }
            case HUMIDITY -> {
                ClimateSensorAvro data = (ClimateSensorAvro) state.getData();
                int actual = data.getHumidity();
                yield checkOperation(operation, actual, condition.getCondition().getValue());
            }
            case CO2LEVEL -> {
                ClimateSensorAvro data = (ClimateSensorAvro) state.getData();
                int actual = data.getCo2Level();
                yield checkOperation(operation, actual, condition.getCondition().getValue());
            }
            case SWITCH -> {
                SwitchSensorAvro data = (SwitchSensorAvro) state.getData();
                int actual = data.getState() ? 1 : 0;
                yield checkOperation(operation, actual, condition.getCondition().getValue());
            }
        };
    }

    private boolean checkOperation(ConditionOperationAvro operationType, Integer expected, Integer actual) {
        return switch (operationType) {
            case EQUALS -> actual.equals(expected);
            case GREATER_THAN -> actual < expected;
            case LOWER_THAN -> actual > expected;
        };
    }
}