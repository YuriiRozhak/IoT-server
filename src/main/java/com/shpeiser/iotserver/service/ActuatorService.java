package com.shpeiser.iotserver.service;

import com.shpeiser.iotserver.model.Actuator;
import com.shpeiser.iotserver.repository.ActuatorRepository;
import com.shpeiser.iotserver.service.messaging.MqttPublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActuatorService {
    private final ActuatorRepository actuatorRepository;
    private final MqttPublisherService mqttPublisherService;

    public List<Actuator> getAllActuators() {
        return actuatorRepository.findAll();
    }

    public void deleteByActuatorId(Long id) {
        actuatorRepository.deleteById(id);
    }

    public Actuator save(Actuator actuator) {
        return actuatorRepository.save(actuator);
    }

    public Actuator enableActuator(Long id) {
        Optional<Actuator> byId = actuatorRepository.findById(id);
        return byId.map(actuator -> {
            mqttPublisherService.publish(actuator.getName(), "ON");
            return actuator;
        }).orElseThrow(() -> new RuntimeException("Actuator not found"));
    }

    public Actuator disableActuator(Long id) {
        Optional<Actuator> byId = actuatorRepository.findById(id);
        return byId.map(actuator -> {
            mqttPublisherService.publish(actuator.getName(), "OFF");
            return actuator;
        }).orElseThrow(() -> new RuntimeException("Actuator not found"));
    }

    public Actuator saveActuatorState(Long id, Boolean enabled) {
        return actuatorRepository.findById(id)
                .map(actuator -> {
                    actuator.setState(enabled);
                    return actuatorRepository.save(actuator);
                })
                .orElseThrow(() -> new RuntimeException("Actuator not found"));
    }
}