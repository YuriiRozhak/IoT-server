package com.example.iotserver.model.weather;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Location location;

    @Embedded
    private CurrentWeather current;

    private LocalDateTime timestamp;

    public WeatherData() {
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
}
