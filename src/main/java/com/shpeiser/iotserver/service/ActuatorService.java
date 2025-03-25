package com.shpeiser.iotserver.service;

import com.shpeiser.iotserver.model.Actuator;
import com.shpeiser.iotserver.repository.ActuatorRepository;
import com.shpeiser.iotserver.service.messaging.MqttPublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return actuatorRepository.findById(id).map(actuator -> {
            mqttPublisherService.publish(actuator.getName(), "ON");
            return actuator;
        }).orElseThrow(() -> new RuntimeException("Actuator not found"));
    }

    public Actuator disableActuator(Long id) {
        return actuatorRepository.findById(id).map(actuator -> {
            mqttPublisherService.publish(actuator.getName(), "OFF");
            return actuator;
        }).orElseThrow(() -> new RuntimeException("Actuator not found"));
    }

    public void switchState(Long id) {
        actuatorRepository.findById(id)
                .map(Actuator::isState)
                .map(active -> active ? disableActuator(id) : enableActuator(id));
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