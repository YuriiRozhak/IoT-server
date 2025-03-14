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
}