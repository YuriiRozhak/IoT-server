package com.shpeiser.iotserver.service;

import com.shpeiser.iotserver.model.Sensor;
import com.shpeiser.iotserver.model.SensorComparator;
import com.shpeiser.iotserver.repository.SensorComparatorRepository;
import com.shpeiser.iotserver.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SensorComparatorService {
    private final SensorComparatorRepository sensorComparatorRepository;
    private final SensorRepository sensorRepository;

    public List<SensorComparator> getAllSensorThresholds() {
        return sensorComparatorRepository.findAll();
    }

    public void deleteBySensorThresholdId(Long id) {
        sensorComparatorRepository.deleteById(id);
    }

    public SensorComparator save(SensorComparator sensorComparator) {
        final Sensor sensor = sensorRepository.findById(sensorComparator.getSensor().getId())
                .orElseThrow(() -> new RuntimeException("Sensor not found"));
        sensorComparator.setSensor(sensor);
        return sensorComparatorRepository.save(sensorComparator);
    }

    public Optional<SensorComparator> getSensorThresholdById(Long id) {
        return sensorComparatorRepository.findById(id);
    }
}