package com.shpeiser.iotserver.repository;

import com.shpeiser.iotserver.model.weather.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    @Query("SELECT w FROM WeatherData w ORDER BY w.timestamp DESC LIMIT 1")
    Optional<WeatherData> findLatestWeatherData();
}