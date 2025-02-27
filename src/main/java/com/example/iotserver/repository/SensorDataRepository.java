package com.example.iotserver.repository;

import com.example.iotserver.model.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorDataRepository extends JpaRepository<SensorData, Long> {

    @Modifying
    @Query("DELETE FROM SensorData WHERE id IN (SELECT id FROM SensorData ORDER BY timestamp ASC LIMIT :deleteCount)")
    void deleteOldestEntries(@Param("deleteCount") int deleteCount);


    List<SensorData> getAllBySensorType(String sensorType);
}
