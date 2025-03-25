package com.shpeiser.iotserver.service.messaging;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "mosqito.local.subscriber", havingValue = "true")
public class MqttSubscriber {
    private static final String[] TOPICS = {"LED", "Buzzer", "fan"};

    public MqttSubscriber(@Value("${mosquito.broker.url}") String broker,
                          @Value("${mosquito.client.id}") String clientId) {
        try {
            MqttClient mqttClient = new MqttClient(broker, clientId, new MemoryPersistence());
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
            mqttClient.subscribe(TOPICS);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
