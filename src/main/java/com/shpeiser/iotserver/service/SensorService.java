package com.shpeiser.iotserver.service;

import com.shpeiser.iotserver.model.Sensor;
import com.shpeiser.iotserver.repository.SensorRepository;
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
