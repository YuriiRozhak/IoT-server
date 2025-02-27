package com.example.iotserver.controller;

import com.example.iotserver.model.SensorData;
import com.example.iotserver.repository.SensorDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SensorUIController {


    private final SensorDataRepository repository;

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        List<SensorData> sensorData = repository.findAll();
        model.addAttribute("sensorData", sensorData);
        return "dashboard"; // Loads the Thymeleaf template "dashboard.html"
    }
}
