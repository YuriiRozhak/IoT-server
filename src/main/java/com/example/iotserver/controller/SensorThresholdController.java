package com.example.iotserver.controller;

import com.example.iotserver.model.SensorThreshold;
import com.example.iotserver.service.SensorThresholdService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sensor-threshold")
@RequiredArgsConstructor
public class SensorThresholdController {

    private final SensorThresholdService sensorThresholdService;

    @GetMapping("/all")
    public List<SensorThreshold> getAllSensorThresholds() {
        return sensorThresholdService.getAllSensorThresholds();
    }

    @DeleteMapping("/{id}")
    public void deleteBySensorThresholdId(@PathVariable(name = "id") Long id) {
        sensorThresholdService.deleteBySensorThresholdId(id);
    }

    @PostMapping("/add")
    public SensorThreshold saveSensorThreshold(@RequestBody SensorThreshold sensorThreshold) {
        return sensorThresholdService.save(sensorThreshold);
    }

    @GetMapping("/{id}")
    public Optional<SensorThreshold> getSensorThresholdById(@PathVariable(name = "id") Long id) {
        return sensorThresholdService.getSensorThresholdById(id);
    }
}