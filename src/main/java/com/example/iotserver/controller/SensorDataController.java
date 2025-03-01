package com.example.iotserver.controller;

import com.example.iotserver.model.Sensor;
import com.example.iotserver.model.SensorData;
import com.example.iotserver.model.SensorSettings;
import com.example.iotserver.repository.SensorDataRepository;
import com.example.iotserver.repository.SensorRepository;
import com.example.iotserver.repository.SensorSettingsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/data")
public class SensorDataController {

    private final Integer defaultMaxRecords;

    private final SensorDataRepository sensorDataRepository;
    private final SensorRepository sensorRepository;
    private final SensorSettingsRepository sensorSettingsRepository;

    public SensorDataController(@Value("${sensor.records.count:1000}") Integer defaultMaxRecords,
                                SensorDataRepository sensorDataRepository,
                                SensorRepository sensorRepository,
                                SensorSettingsRepository sensorSettingsRepository) {
        this.defaultMaxRecords = defaultMaxRecords;
        this.sensorDataRepository = sensorDataRepository;
        this.sensorRepository = sensorRepository;
        this.sensorSettingsRepository = sensorSettingsRepository;
    }

    @PostMapping("/add")
    public SensorData addSensorData(@RequestBody SensorData data) {
        Sensor sensor = Optional.ofNullable(data.getSensor())
                .map(Sensor::getType)
                .map(sensorRepository::findByType)
                .orElse(null);
        if (sensor == null) {
            sensor = new Sensor();
            sensor.setType(data.getSensor().getType());
            sensor.setDescription("Auto-created sensor for " + data.getSensor().getType());
            sensor = sensorRepository.save(sensor);
        }

        // Create and save SensorData
        SensorData sensorData = new SensorData();
        sensorData.setSensor(sensor);
        sensorData.setValue(data.getValue());
        sensorData.setTimestamp(data.getTimestamp() != null ? data.getTimestamp() : LocalDateTime.now());

        SensorData save = sensorDataRepository.save(sensorData);

        long count = sensorDataRepository.countBySensor_Id(save.getSensor().getId());
        long MAX_RECORDS = Optional.ofNullable(sensorSettingsRepository.findBySensor(save.getSensor()))
                .map(SensorSettings::getMaxRecordsStored)
                .orElse(defaultMaxRecords);  // Keep only the latest 1000 records

        if (count > MAX_RECORDS) {
            sensorDataRepository.deleteOldestEntries(count - MAX_RECORDS, save.getSensor().getId());
        }

        return save;
    }

    @GetMapping("/all")
    public List<SensorData> getAllSensorData() {
        return sensorDataRepository.findAll();
    }
    @GetMapping("/all/{sensorId}")
    public List<SensorData> getAllSensorDataBySensorType(@PathVariable(name = "sensorId") Long sensorId) {
        return sensorDataRepository.getAllBySensor_Id(sensorId);
    }

}
