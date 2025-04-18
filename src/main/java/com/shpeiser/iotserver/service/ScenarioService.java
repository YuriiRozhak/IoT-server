package com.shpeiser.iotserver.service;

import com.shpeiser.iotserver.model.*;
import com.shpeiser.iotserver.model.Comparator;
import com.shpeiser.iotserver.repository.ActuatorRepository;
import com.shpeiser.iotserver.repository.ScenarioRepository;
import com.shpeiser.iotserver.repository.SensorComparatorRepository;
import com.shpeiser.iotserver.repository.SensorRepository;
import com.shpeiser.iotserver.service.messaging.MqttPublisherService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScenarioService {
    private final ScenarioRepository scenarioRepository;
    private final SensorComparatorRepository sensorComparatorRepository;
    private final ActuatorRepository actuatorRepository;
    private final MqttPublisherService mqttPublisherService;
    private final SensorRepository sensorRepository;

    public List<Scenario> getAllScenarios() {
        return scenarioRepository.findAll();
    }

    public void deleteByScenarioId(Long id) {
        scenarioRepository.deleteById(id);
    }

    public Scenario save(Scenario scenario) {
        final List<Long> actuatorsIds = scenario.getActuators().stream().map(Actuator::getId).toList();
        final List<Actuator> actuators = actuatorRepository.findAllById(actuatorsIds);
        scenario.getSensorComparators()
                .forEach(sensorComparator -> sensorComparator.setSensor(
                        sensorRepository.findById(sensorComparator.getSensor().getId()).orElseThrow(() ->
                                new RuntimeException("Sensor not found")))
                );
        final List<SensorComparator> savedSensorComparators = sensorComparatorRepository.saveAll(scenario.getSensorComparators());
        scenario.setActuators(actuators);
        scenario.setSensorComparators(savedSensorComparators);
        return scenarioRepository.save(scenario);
    }

    public Optional<Scenario> getScenarioById(Long id) {
        return scenarioRepository.findById(id);
    }

    public void checkAllScenarios(Collection<SensorData> sensorData) {
        final List<Scenario> allScenarios = scenarioRepository.findAll();

        allScenarios.stream()
                .filter(Scenario::isActive)
                .forEach(scenario -> checkScenarioOverData(sensorData, scenario));

    }

    private void checkScenarioOverData(Collection<SensorData> sensorData, Scenario scenario) {
        final List<SensorComparator> sensorComparators = scenario.getSensorComparators();
        if (!(CollectionUtils.isEmpty(scenario.getActuators()) || CollectionUtils.isEmpty(sensorComparators)
                || CollectionUtils.isEmpty(sensorData))) {
            final Map<Long, List<SensorComparator>> sensorThresholdsBySensorId = sensorComparators.stream()
                    .collect(Collectors.groupingBy(st -> st.getSensor().getId()));
            boolean actuatorsShouldBeActivated = shouldActuatorsBeActivated(sensorData, scenario, sensorThresholdsBySensorId);
            sentCommandToActuators(scenario.getActuators(), actuatorsShouldBeActivated);
        }
    }

    private boolean shouldActuatorsBeActivated(Collection<SensorData> sensorData,
                                               Scenario scenario,
                                               Map<Long, List<SensorComparator>> sensorComparatorsBySensorId) {
        List<SensorData> sensorsFromScenario = sensorData.stream()
                .filter(sd -> sensorComparatorsBySensorId.containsKey(sd.getSensor().getId()))
                .toList();
        return switch (scenario.getConditionType()) {
            case ANY -> sensorsFromScenario.stream().anyMatch(sd -> {
                Long dataSensorId = sd.getSensor().getId();
                List<SensorComparator> comparators = sensorComparatorsBySensorId.getOrDefault(dataSensorId, Collections.emptyList());
                return verifyThresholdsOverData(comparators, sd);
            });
            case NONE -> sensorsFromScenario.stream().noneMatch(sd -> {
                Long dataSensorId = sd.getSensor().getId();
                List<SensorComparator> comparators = sensorComparatorsBySensorId.getOrDefault(dataSensorId, Collections.emptyList());
                return verifyThresholdsOverData(comparators, sd);
            });
            default -> sensorsFromScenario.stream().allMatch(sd -> {
                Long dataSensorId = sd.getSensor().getId();
                List<SensorComparator> comparators = sensorComparatorsBySensorId.getOrDefault(dataSensorId, Collections.emptyList());
                return verifyThresholdsOverData(comparators, sd);
            });
        };
    }

    private void sentCommandToActuators(List<Actuator> actuators, boolean actuatorCondition) {
        if (actuatorCondition) {
            actuators.forEach(actuator ->
                    mqttPublisherService.publishActuatorState(actuator.getName(), ActuatorState.ON));
        } else {
            actuators.forEach(actuator ->
                    mqttPublisherService.publishActuatorState(actuator.getName(), ActuatorState.OFF));
        }
    }

    private boolean verifyThresholdsOverData(List<SensorComparator> th, SensorData sd) {
        return th.stream().anyMatch(sensorThreshold -> verifyThresholdsOverData(sensorThreshold, sd));
    }

    private boolean verifyThresholdsOverData(SensorComparator sensorComparator, SensorData sd) {
        return checkCondition(sensorComparator.getValue(), sd.getValue(), sensorComparator.getComparator());
    }

    private boolean checkCondition(@NotNull Double thresholdValue, @NotNull double sensorValue, String comparator) {
        return Comparator.getBySymbol(comparator).check(sensorValue, thresholdValue);
    }

    public Scenario switchState(Long id) {
        return scenarioRepository.findById(id)
                .map(scenario -> {
                    scenario.setActive(!scenario.isActive());
                    return scenarioRepository.save(scenario);
                })
                .orElseThrow(() -> new RuntimeException("Scenario not found"));
    }
}