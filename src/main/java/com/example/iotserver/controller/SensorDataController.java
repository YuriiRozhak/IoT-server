package com.example.iotserver.controller;

import com.example.iotserver.model.SensorData;
import com.example.iotserver.repo.SensorDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/sensor")
@RequiredArgsConstructor
public class SensorDataController {


    private final SensorDataRepository repository;

    @PostMapping("/add")
    public SensorData addSensorData(@RequestBody SensorData data) {
        data.setTimestamp(LocalDateTime.now());
        SensorData save = repository.save(data);

        long count = repository.count();
        int MAX_RECORDS = 1000;  // Keep only the latest 1000 records

        if (count > MAX_RECORDS) {
            repository.deleteOldestEntries((int) (count - MAX_RECORDS));
        }

        return save;
    }

    @GetMapping("/all")
    public List<SensorData> getAllSensorData() {
        return repository.findAll();
    }
    @GetMapping("/all/{sensorType}")
    public List<SensorData> getAllSensorDataBySensorType(@PathVariable(name = "sensorType") String sensorType) {
        return repository.getAllBySensorType(sensorType);
    }
}
