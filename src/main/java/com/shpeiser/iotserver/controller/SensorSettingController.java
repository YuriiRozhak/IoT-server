package com.shpeiser.iotserver.controller;

import com.shpeiser.iotserver.model.SensorSettings;
import com.shpeiser.iotserver.service.SensorSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/settings")
@RequiredArgsConstructor
public class SensorSettingController {


    private final SensorSettingsService sensorSettingsService;

    @PostMapping("/set")
    public SensorSettings setSettings(@RequestBody SensorSettings sensorSettings) {
        return sensorSettingsService.setSettings(sensorSettings);
    }

    @GetMapping("/get/{sensorId}")
    public SensorSettings getSettings(@PathVariable(name = "sensorId") long sensorId) {
        return sensorSettingsService.getSettings(sensorId);
    }

    @GetMapping("/all")
    public List<SensorSettings> getAllSettings() {
        return sensorSettingsService.getAllSettings();
    }

}
