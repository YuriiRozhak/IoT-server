package com.shpeiser.iotserver.service.messaging;

import com.shpeiser.iotserver.model.ActuatorState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MqttPublisherService {

    private final MqttPublisher mqttPublisher;

    public String publish(String topic, String message) {
        mqttPublisher.publish(topic, message);
        return "Message sent to topic: " + topic;
    }

    public String publishActuatorState(String topic, ActuatorState actuatorState) {
        mqttPublisher.publish(topic, actuatorState.toString());
        return "State \"" + actuatorState + "\" sent to topic: \"" + topic + "\"";
    }

}