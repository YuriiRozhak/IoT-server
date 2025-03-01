package com.example.iotserver.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensor sensor;

    private double minValue;  // Minimum threshold
    private double maxValue;  // Maximum threshold
    private int maxRecordsStored; // Max data records stored per sensor
}
