package com.example.iotserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healtcheck")
public class HealthCheckController {
    @GetMapping
    public ResponseEntity<Void> healtcheck() {
        return ResponseEntity.status(200).build();
    }
}
