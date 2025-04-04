package com.shpeiser.iotserver.model;

import com.shpeiser.iotserver.converter.LocalDateTimeConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensor sensor;

    @NotNull
    private double value;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime timestamp;

    public static SensorData asFromSensor(double value, String sensorType, String sensorDescription) {
        return new SensorData(null, new Sensor(null, sensorType, sensorDescription), value, LocalDateTime.now());
    }
}
