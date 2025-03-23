package com.shpeiser.iotserver.model;

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

    public static SensorSettings createDefaultSettings(int maxRecordsStored) {
        SensorSettings sensorSettings = new SensorSettings();
        sensorSettings.setMaxRecordsStored(maxRecordsStored);
        sensorSettings.setMinValue(Double.MIN_VALUE);
        sensorSettings.setMaxValue(Double.MAX_VALUE);
        return sensorSettings;
    }
}
