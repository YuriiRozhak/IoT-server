package com.example.iotserver.service;

import com.example.iotserver.model.Sensor;
import com.example.iotserver.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SensorService {
    private final SensorRepository sensorRepository;

    public List<Sensor> getAllSensors() {
        return sensorRepository.findAll();
    }

    public void deleteBySensorId(Long id) {
        sensorRepository.deleteById(id);
    }
}
