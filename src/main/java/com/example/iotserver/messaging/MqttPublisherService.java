package com.example.iotserver.messaging;

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

}