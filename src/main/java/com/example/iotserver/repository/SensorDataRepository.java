package com.example.iotserver.repository;

import com.example.iotserver.model.SensorData;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorDataRepository extends JpaRepository<SensorData, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM SensorData s WHERE s.id IN " +
            "(SELECT sd.id FROM SensorData sd WHERE sd.sensor.id = :sensorId ORDER BY sd.timestamp ASC LIMIT :deleteCount)")
    void deleteOldestEntries( @Param("deleteCount") long deleteCount, @Param("sensorId") long sensorId);



    List<SensorData> getAllBySensor_Id(Long sensorId);

    long countBySensor_Id(Long sensorId);
}
