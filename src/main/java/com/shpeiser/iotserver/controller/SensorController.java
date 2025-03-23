package com.shpeiser.iotserver.controller;

import com.shpeiser.iotserver.model.Sensor;
import com.shpeiser.iotserver.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sensor")
@RequiredArgsConstructor
public class SensorController {

    private final SensorService sensorService;

    @GetMapping("/all")
    public List<Sensor> getAllSensors() {
        return sensorService.getAllSensors();
    }
}
