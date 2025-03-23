package com.shpeiser.iotserver.controller;

import com.shpeiser.iotserver.model.SensorComparator;
import com.shpeiser.iotserver.service.SensorComparatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sensor-threshold")
@RequiredArgsConstructor
public class SensorComparatorController {

    private final SensorComparatorService sensorComparatorService;

    @GetMapping("/all")
    public List<SensorComparator> getAllSensorThresholds() {
        return sensorComparatorService.getAllSensorThresholds();
    }

    @DeleteMapping("/{id}")
    public void deleteBySensorThresholdId(@PathVariable(name = "id") Long id) {
        sensorComparatorService.deleteBySensorThresholdId(id);
    }

    @PostMapping("/add")
    public SensorComparator saveSensorThreshold(@RequestBody SensorComparator sensorComparator) {
        return sensorComparatorService.save(sensorComparator);
    }

    @GetMapping("/{id}")
    public Optional<SensorComparator> getSensorThresholdById(@PathVariable(name = "id") Long id) {
        return sensorComparatorService.getSensorThresholdById(id);
    }
}