package com.example.iotserver.service;

import com.example.iotserver.model.Scenario;
import com.example.iotserver.repository.ScenarioRepository;
import com.example.iotserver.repository.SensorThresholdRepository;
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