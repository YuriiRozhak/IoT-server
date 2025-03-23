package com.shpeiser.iotserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alive")
public class HealthCheckController {
    @GetMapping
    public ResponseEntity<Void> alive() {
        return ResponseEntity.status(200).build();
    }
}
