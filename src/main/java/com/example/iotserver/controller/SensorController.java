package com.example.iotserver.controller;

import com.example.iotserver.model.Sensor;
import com.example.iotserver.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sensor")
@RequiredArgsConstructor
public class SensorController {


    private final SensorRepository sensorRepository;

    @GetMapping("/all")
    public List<Sensor> getAllSensors() {
        return sensorRepository.findAll();
    }
}
