package com.example.iotserver.controller;

import com.example.iotserver.model.Actuator;
import com.example.iotserver.service.ActuatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actuator")
@RequiredArgsConstructor
public class ActuatorController {

    private final ActuatorService actuatorService;

    @GetMapping("/all")
    public List<Actuator> getAllActuators() {
        return actuatorService.getAllActuators();
    }

    @DeleteMapping("/{id}")
    public void deleteByActuatorId(@PathVariable(name = "id") Long id) {
        actuatorService.deleteByActuatorId(id);
    }

    @PostMapping("/add")
    public Actuator saveActuator(@RequestBody Actuator actuator) {
        return actuatorService.save(actuator);
    }

    @PostMapping("/enable/{id}")
    public Actuator enableActuator(@PathVariable(name = "id") Long id) {
        return actuatorService.enableActuator(id);
    }

    @PostMapping("/disable/{id}")
    public Actuator disableActuator(@PathVariable(name = "id") Long id) {
        return actuatorService.disableActuator(id);
    }

    @PostMapping("/state/{id}/{enabled}")
    public Actuator saveActuatorState(@PathVariable(name = "id") Long id, @PathVariable(name = "enabled") Boolean enabled) {
        return actuatorService.saveActuatorState(id, enabled);
    }

}