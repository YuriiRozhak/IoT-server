package com.example.iotserver.service;

import com.example.iotserver.model.weather.WeatherData;
import com.example.iotserver.repository.WeatherApiRepository;
import com.example.iotserver.repository.WeatherDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherDataService {

    private final WeatherDataRepository weatherDataRepository;
    private final WeatherApiRepository weatherApiRepository;

    public List<WeatherData> getAllWeatherData() {
        return weatherDataRepository.findAll();
    }

    public Optional<WeatherData> getWeatherDataById(Long id) {
        return weatherDataRepository.findById(id);
    }

    public WeatherData saveWeatherData(WeatherData weatherData) {
        return weatherDataRepository.save(weatherData);
    }

    public void deleteWeatherDataById(Long id) {
        weatherDataRepository.deleteById(id);
    }

    public Optional<WeatherData> getLatestWeatherData() {
        return weatherDataRepository.findLatestWeatherData();
    }

    public WeatherData getCurrentWeatherFromAPI(String apiKey, String coordinates) {
        return weatherApiRepository.getCurrentWeather(apiKey, coordinates);
    }
}