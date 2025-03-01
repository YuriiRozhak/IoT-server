package com.example.iotserver.controller;

import com.example.iotserver.model.SensorSettings;
import com.example.iotserver.repository.SensorSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/settings")
@RequiredArgsConstructor
public class SensorSettingController {


    private final SensorSettingsRepository repository;
    private final SensorSettingsRepository sensorSettingsRepository;

    @PostMapping("/set")
    public SensorSettings setSettings(@RequestBody SensorSettings sensorSettings) {
        SensorSettings existing = repository.findBySensor_Id(sensorSettings.getSensor().getId());
        return Optional.ofNullable(existing).map(ex -> {
                    ex.setMaxValue(sensorSettings.getMaxValue());
                    ex.setMinValue(sensorSettings.getMinValue());
                    ex.setMaxRecordsStored(sensorSettings.getMaxRecordsStored());
                    return ex;
                }).map(sensorSettingsRepository::save)
                .orElseGet(() -> repository.save(sensorSettings));
    }

    @GetMapping("/get/{sensorId}")
    public SensorSettings getSettings(@PathVariable(name = "sensorId") long sensorId) {
        return repository.findBySensor_Id(sensorId);
    }

    @GetMapping("/all")
    public List<SensorSettings> getAllSettings() {
        return repository.findAll();
    }
}
