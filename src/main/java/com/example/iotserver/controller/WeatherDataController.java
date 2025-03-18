package com.example.iotserver.controller;

import com.example.iotserver.model.weather.WeatherData;
import com.example.iotserver.service.WeatherDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/weatherdata")
@RequiredArgsConstructor
public class WeatherDataController {

    private final WeatherDataService weatherDataService;

    @GetMapping("/all")
    public List<WeatherData> getAllWeatherData() {
        return weatherDataService.getAllWeatherData();
    }

    @GetMapping("/one/{id}")
    public Optional<WeatherData> getWeatherDataById(@PathVariable(name = "id") Long id) {
        return weatherDataService.getWeatherDataById(id);
    }

    @PostMapping("/add")
    public WeatherData saveWeatherData(@RequestBody WeatherData weatherData) {
        return weatherDataService.saveWeatherData(weatherData);
    }

    @DeleteMapping("/{id}")
    public void deleteWeatherDataById(@PathVariable(name = "id") Long id) {
        weatherDataService.deleteWeatherDataById(id);

    }

    @GetMapping("/latest")
    public Optional<WeatherData> getLatestWeatherData() {
        return weatherDataService.getLatestWeatherData();
    }

    @GetMapping("/current")
    public WeatherData getCurrentWeatherFromAPI(@RequestParam(name = "api-key") String apiKey, @RequestParam(name = "coordinates") String coordinates) {
        return weatherDataService.getCurrentWeatherFromAPI(apiKey, coordinates);
    }

}