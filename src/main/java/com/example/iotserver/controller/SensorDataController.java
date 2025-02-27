package com.example.iotserver.controller;

import com.example.iotserver.model.SensorData;
import com.example.iotserver.repo.SensorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensor")
public class SensorDataController {

    @Autowired
    private SensorDataRepository repository;

    @PostMapping("/add")
    public SensorData addSensorData(@RequestBody SensorData data) {
        return repository.save(data);
    }

    @GetMapping("/all")
    public List<SensorData> getAllSensorData() {
        return repository.findAll();
    }
}
