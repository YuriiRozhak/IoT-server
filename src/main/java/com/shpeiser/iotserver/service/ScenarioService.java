package com.shpeiser.iotserver.service;

import com.shpeiser.iotserver.model.Scenario;
import com.shpeiser.iotserver.repository.ScenarioRepository;
import com.shpeiser.iotserver.repository.SensorThresholdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScenarioService {
    private final ScenarioRepository scenarioRepository;
    private final SensorThresholdRepository sensorThresholdRepository;

    public List<Scenario> getAllScenarios() {
        return scenarioRepository.findAll();
    }

    public void deleteByScenarioId(Long id) {
        scenarioRepository.deleteById(id);
    }

    public Scenario save(Scenario scenario) {
        sensorThresholdRepository.saveAll(scenario.getSensorThresholds());
        return scenarioRepository.save(scenario);
    }

    public Optional<Scenario> getScenarioById(Long id) {
        return scenarioRepository.findById(id);
    }
}