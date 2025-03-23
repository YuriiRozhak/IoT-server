package com.shpeiser.iotserver.repository;

import com.shpeiser.iotserver.model.Sensor;
import com.shpeiser.iotserver.model.SensorSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorSettingsRepository extends JpaRepository<SensorSettings, Long> {
    SensorSettings findBySensor(Sensor sensor);
    SensorSettings findBySensor_Id(Long sensorId);

    void deleteBySensor_Id(Long id);
}
