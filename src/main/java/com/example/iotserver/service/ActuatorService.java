package com.example.iotserver.service;

import com.example.iotserver.model.Actuator;
import com.example.iotserver.repository.ActuatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActuatorService {
    private final ActuatorRepository actuatorRepository;

    public List<Actuator> getAllActuators() {
        return actuatorRepository.findAll();
    }

    public void deleteByActuatorId(Long id) {
        actuatorRepository.deleteById(id);
    }

    public Actuator save(Actuator actuator) {
        return actuatorRepository.save(actuator);
    }
}