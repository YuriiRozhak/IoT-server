package com.shpeiser.iotserver.service.messaging;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Component;

@Component
public class MqttPublisher {
    private static final String BROKER = "tcp://mosquitto:1883"; // Use the Docker container name
    private static final String CLIENT_ID = "SpringBootPublisher";
    private MqttClient mqttClient;

    public MqttPublisher() {
        try {
            mqttClient = new MqttClient(BROKER, CLIENT_ID, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            mqttClient.connect(options);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publish(String topic, String payload) {
        try {
            MqttMessage message = new MqttMessage(payload.getBytes());
            message.setQos(1);  // QoS 1: Delivered at least once
            mqttClient.publish(topic, message);
            System.out.println("Message sent to topic: " + topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
