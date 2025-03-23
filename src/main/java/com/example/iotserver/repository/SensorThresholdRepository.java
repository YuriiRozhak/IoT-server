package com.example.iotserver.repository;

import com.example.iotserver.model.SensorThreshold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorThresholdRepository extends JpaRepository<SensorThreshold, Long> {
}