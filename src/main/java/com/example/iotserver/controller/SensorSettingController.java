package com.example.iotserver.controller;

import com.example.iotserver.model.SensorSettings;
import com.example.iotserver.repository.SensorSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/settings")
@RequiredArgsConstructor
public class SensorSettingController {


    private final SensorSettingsRepository repository;

    @PostMapping("/set")
    public SensorSettings setThreshold(@RequestBody SensorSettings threshold) {
        return repository.save(threshold);
    }

    @GetMapping("/get/{sensorId}")
    public SensorSettings getSettings(@PathVariable long sensorId) {
        return repository.findBySensor_Id(sensorId);
    }

    @GetMapping("/all")
    public List<SensorSettings> getAllSettings() {
        return repository.findAll();
    }
}
