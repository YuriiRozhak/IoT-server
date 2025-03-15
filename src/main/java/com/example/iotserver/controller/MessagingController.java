package com.example.iotserver.controller;

import com.example.iotserver.messaging.MqttPublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messaging")
@RequiredArgsConstructor
@Slf4j
public class MessagingController {

    private final MqttPublisherService mqttPublisherService;

    @PostMapping("/publish")
    public String sendMessage(@RequestParam(name = "topic") String topic, @RequestParam(name = "message") String message) {
        mqttPublisherService.publish(topic, message);
        return "Message sent to topic: " + topic;
    }


}
