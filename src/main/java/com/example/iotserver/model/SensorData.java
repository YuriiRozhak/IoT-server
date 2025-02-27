package com.example.iotserver.model;

import com.example.iotserver.converter.LocalDateTimeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @Column(columnDefinition = "TEXT")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime timestamp;

}
