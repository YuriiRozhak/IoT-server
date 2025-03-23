package com.example.iotserver.messaging;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "mosqito.local.subscriber", havingValue = "true")
public class MqttSubscriber {
    private static final String BROKER = "tcp://mosquitto:1883"; // Use the Docker container name
    private static final String CLIENT_ID = "SpringBootSubscriber";
    private static final String TOPIC = "actuator/state";

    public MqttSubscriber() {
        try {
            MqttClient mqttClient = new MqttClient(BROKER, CLIENT_ID, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("Connection lost: " + cause.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    System.out.println("Message received from topic " + topic + ": " + new String(message.getPayload()));
                    // Process the actuator state update here
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("Delivery complete: " + token.isComplete());
                }
            });

            mqttClient.connect(options);
            mqttClient.subscribe(TOPIC);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
