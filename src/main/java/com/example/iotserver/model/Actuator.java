package com.example.iotserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Actuator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;        // "DHT22", "MQ-3", "MQ-135"
    private String description; // Optional details about the sensor
    private short pinIn;          // GPIO Input pin number
    private short pinOut;          // GPIO Output pin number
    private boolean state; // true or false

}
