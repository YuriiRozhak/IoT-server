package com.shpeiser.iotserver.service;

import com.shpeiser.iotserver.model.SensorThreshold;
import com.shpeiser.iotserver.repository.SensorThresholdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SensorThresholdService {
    private final SensorThresholdRepository sensorThresholdRepository;

    public List<SensorThreshold> getAllSensorThresholds() {
        return sensorThresholdRepository.findAll();
    }

    public void deleteBySensorThresholdId(Long id) {
        sensorThresholdRepository.deleteById(id);
    }

    public SensorThreshold save(SensorThreshold sensorThreshold) {
        return sensorThresholdRepository.save(sensorThreshold);
    }

    public Optional<SensorThreshold> getSensorThresholdById(Long id) {
        return sensorThresholdRepository.findById(id);
    }
}