package com.shpeiser.iotserver.service;

import com.shpeiser.iotserver.model.SensorSettings;
import com.shpeiser.iotserver.repository.SensorSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SensorSettingsService {

    private final SensorSettingsRepository sensorSettingsRepository;


    public SensorSettings setSettings(SensorSettings sensorSettings) {
        SensorSettings existing = sensorSettingsRepository.findBySensor_Id(sensorSettings.getSensor().getId());
        return Optional.ofNullable(existing).map(ex -> {
                    ex.setMaxValue(sensorSettings.getMaxValue());
                    ex.setMinValue(sensorSettings.getMinValue());
                    ex.setMaxRecordsStored(sensorSettings.getMaxRecordsStored());
                    return ex;
                }).map(sensorSettingsRepository::save)
                .orElseGet(() -> sensorSettingsRepository.save(sensorSettings));
    }


    public SensorSettings getSettings(long sensorId) {
        return sensorSettingsRepository.findBySensor_Id(sensorId);
    }


    public List<SensorSettings> getAllSettings() {
        return sensorSettingsRepository.findAll();
    }

    public void deleteBySensorId(Long id) {
        sensorSettingsRepository.deleteBySensor_Id(id);
    }
}
