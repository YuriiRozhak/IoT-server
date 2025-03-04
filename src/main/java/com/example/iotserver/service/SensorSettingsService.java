package com.example.iotserver.service;

import com.example.iotserver.model.SensorSettings;
import com.example.iotserver.repository.SensorSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SensorSettingsService {

    private final SensorSettingsRepository repository;
    private final SensorSettingsRepository sensorSettingsRepository;


    public SensorSettings setSettings(SensorSettings sensorSettings) {
        SensorSettings existing = repository.findBySensor_Id(sensorSettings.getSensor().getId());
        return Optional.ofNullable(existing).map(ex -> {
                    ex.setMaxValue(sensorSettings.getMaxValue());
                    ex.setMinValue(sensorSettings.getMinValue());
                    ex.setMaxRecordsStored(sensorSettings.getMaxRecordsStored());
                    return ex;
                }).map(sensorSettingsRepository::save)
                .orElseGet(() -> repository.save(sensorSettings));
    }


    public SensorSettings getSettings(long sensorId) {
        return repository.findBySensor_Id(sensorId);
    }


    public List<SensorSettings> getAllSettings() {
        return repository.findAll();
    }
}
