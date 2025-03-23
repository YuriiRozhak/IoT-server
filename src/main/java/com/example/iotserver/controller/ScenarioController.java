package com.example.iotserver.controller;

import com.example.iotserver.model.Scenario;
import com.example.iotserver.service.ScenarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/scenario")
@RequiredArgsConstructor
public class ScenarioController {

    private final ScenarioService scenarioService;

    @GetMapping("/all")
    public List<Scenario> getAllScenarios() {
        return scenarioService.getAllScenarios();
    }

    @DeleteMapping("/{id}")
    public void deleteByScenarioId(@PathVariable(name = "id") Long id) {
        scenarioService.deleteByScenarioId(id);
    }

    @PostMapping("/add")
    public Scenario saveScenario(@RequestBody Scenario scenario) {
        return scenarioService.save(scenario);
    }

    @GetMapping("/{id}")
    public Optional<Scenario> getScenarioById(@PathVariable(name = "id") Long id) {
        return scenarioService.getScenarioById(id);
    }
}