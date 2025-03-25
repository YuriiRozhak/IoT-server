package com.shpeiser.iotserver.scheduler;

import com.shpeiser.iotserver.model.SensorData;
import com.shpeiser.iotserver.model.weather.WeatherData;
import com.shpeiser.iotserver.repository.WeatherApiRepository;
import com.shpeiser.iotserver.service.SensorDataService;
import com.shpeiser.iotserver.service.WeatherDataService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@ConditionalOnProperty(name = "openweather.api.enabled", havingValue = "true")
public class WeatherDataScheduler {

    private final WeatherDataService weatherDataService;
    private final WeatherApiRepository weatherApiRepository;
    private final SensorDataService sensorDataService;
    private final String location;
    private final String weatherApiKey;
    private final boolean saveAsSensor;

    public WeatherDataScheduler(WeatherDataService weatherDataService,
                                WeatherApiRepository weatherApiRepository,
                                SensorDataService sensorDataService,
                                @Value("${openweather.api.location}") String location,
                                @Value("${openweather.api.save.as.sensor:true}") boolean saveAsSensor,
                                @Value("${openweather.api.key}") String weatherApiKey) {
        this.weatherDataService = weatherDataService;
        this.weatherApiRepository = weatherApiRepository;
        this.sensorDataService = sensorDataService;
        this.location = location;
        this.weatherApiKey = weatherApiKey;
        this.saveAsSensor = saveAsSensor;
    }

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.MINUTES) // 1 hour in milliseconds
    public void fetchAndStoreWeatherData() {
        WeatherData weatherData = weatherApiRepository.getCurrentWeather(weatherApiKey, location);
        if (weatherData != null) {
            weatherDataService.saveWeatherData(weatherData);
            if (saveAsSensor) {
                this.saveAsSensorData(weatherData);
            }
        }
    }

    private void saveAsSensorData(WeatherData weatherData) {
        double tempC = weatherData.getCurrent().getTempC();
        double humidity = weatherData.getCurrent().getHumidity();
        double pressure = weatherData.getCurrent().getPressureMb();
        SensorData fromSensorTemp = SensorData.asFromSensor(tempC, "temperature-from-API", "");
        SensorData fromSensorHunmidity = SensorData.asFromSensor(humidity, "humidity-from-API", "");
        SensorData fromSensorPressure = SensorData.asFromSensor(pressure, "pressure-from-API", "");
        sensorDataService.addAllSensorData(List.of(fromSensorHunmidity, fromSensorPressure, fromSensorTemp));
    }
}