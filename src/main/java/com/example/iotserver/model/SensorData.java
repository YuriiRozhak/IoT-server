package com.example.iotserver.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SensorData")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sensorType; // "DHT22", "MQ-3", "MQ-135"
    private double value;
    private String timestamp;
}
