package com.example.iotserver.controller;

import com.example.iotserver.model.SensorData;
import com.example.iotserver.service.SensorDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class SensorDataController {
    private final SensorDataService sensorDataService;


    @PostMapping("/add")
    public SensorData addSensorData(@RequestBody SensorData data) {

        return sensorDataService.addSensorData(data);
    }

    @PostMapping("/add/all")
    public List<SensorData> addListSensorData(@RequestBody List<SensorData> data) {

        return sensorDataService.addAllSensorData(data);
    }

    @GetMapping("/all")
    public List<SensorData> getAllSensorData() {
        return sensorDataService.getAllSensorData();
    }

    @GetMapping("/all/{sensorId}")
    public List<SensorData> getAllSensorDataBySensorType(@PathVariable(name = "sensorId") Long sensorId) {
        return sensorDataService.getAllSensorDataBySensorType(sensorId);
    }

}
