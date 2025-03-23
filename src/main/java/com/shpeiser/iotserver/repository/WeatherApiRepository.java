package com.shpeiser.iotserver.repository;

import com.shpeiser.iotserver.model.weather.WeatherData;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Repository
public class WeatherApiRepository {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String WEATHER_API_URL = "http://api.weatherapi.com/v1/current.json?key={key}&q={q}";

    public WeatherData getCurrentWeather(String apiKey, String coordinates) {
        Map<String, String> paramMap = Map.of("key", apiKey, "q", coordinates);
        return restTemplate.getForObject(WEATHER_API_URL, WeatherData.class, paramMap);
    }
}
