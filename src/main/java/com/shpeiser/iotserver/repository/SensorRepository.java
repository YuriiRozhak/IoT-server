package com.shpeiser.iotserver.repository;

import com.shpeiser.iotserver.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {
    Sensor findByType(String type);
}
