package com.shpeiser.iotserver.service;

import com.shpeiser.iotserver.model.Sensor;
import com.shpeiser.iotserver.model.SensorSettings;
import com.shpeiser.iotserver.repository.SensorRepository;
import com.shpeiser.iotserver.repository.SensorSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SensorSettingsService {

    private final SensorSettingsRepository sensorSettingsRepository;
    private final SensorRepository sensorRepository;


    public SensorSettings setSettings(SensorSettings sensorSettings) {
        Sensor sensor = sensorRepository.findById(sensorSettings.getSensor().getId())
                .orElseThrow(() -> new RuntimeException("Sensor not found"));
        sensorSettings.setSensor(sensor);
        return sensorSettingsRepository.save(sensorSettings);
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
