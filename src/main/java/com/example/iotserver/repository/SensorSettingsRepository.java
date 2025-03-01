package com.example.iotserver.repository;

import com.example.iotserver.model.Sensor;
import com.example.iotserver.model.SensorSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorSettingsRepository extends JpaRepository<SensorSettings, Long> {
    SensorSettings findBySensor(Sensor sensor);
    SensorSettings findBySensor_Id(Long sensorId);
}
