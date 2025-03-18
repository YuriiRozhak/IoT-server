package com.example.iotserver.scheduler;

import com.example.iotserver.model.weather.WeatherData;
import com.example.iotserver.repository.WeatherApiRepository;
import com.example.iotserver.service.WeatherDataService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WeatherDataScheduler {

    private final WeatherDataService weatherDataService;
    private final WeatherApiRepository weatherApiRepository;
    private final String location;
    private final String weatherApiKey;

    public WeatherDataScheduler(WeatherDataService weatherDataService,
                                WeatherApiRepository weatherApiRepository,
                                @Value("${openweather.api.location}") String location,
                                @Value("${openweather.api.key}") String weatherApiKey) {
        this.weatherDataService = weatherDataService;
        this.weatherApiRepository = weatherApiRepository;
        this.location = location;
        this.weatherApiKey = weatherApiKey;
    }

    @Scheduled(fixedRate = 3600000) // 1 hour in milliseconds
    public void fetchAndStoreWeatherData() {
        WeatherData weatherData = weatherApiRepository.getCurrentWeather(weatherApiKey, location);
        if (weatherData != null) {
            weatherDataService.saveWeatherData(weatherData);
        }
    }
}