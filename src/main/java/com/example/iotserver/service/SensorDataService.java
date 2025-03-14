package com.example.iotserver.service;

import com.example.iotserver.model.Sensor;
import com.example.iotserver.model.SensorData;
import com.example.iotserver.model.SensorSettings;
import com.example.iotserver.repository.SensorDataRepository;
import com.example.iotserver.repository.SensorRepository;
import com.example.iotserver.repository.SensorSettingsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class SensorDataService {

    private final Integer defaultMaxRecords;

    private final SensorDataRepository sensorDataRepository;
    private final SensorRepository sensorRepository;
    private final SensorSettingsRepository sensorSettingsRepository;

    public SensorDataService(@Value("${sensor.records.count:1000}") Integer defaultMaxRecords,
                             SensorDataRepository sensorDataRepository,
                             SensorRepository sensorRepository,
                             SensorSettingsRepository sensorSettingsRepository) {
        this.defaultMaxRecords = defaultMaxRecords;
        this.sensorDataRepository = sensorDataRepository;
        this.sensorRepository = sensorRepository;
        this.sensorSettingsRepository = sensorSettingsRepository;
    }


    public SensorData addSensorData(SensorData data) {
        final Sensor sensor = getSensor(data);

        // Create and save SensorData
        final SensorData sensorData = new SensorData();
        sensorData.setSensor(sensor);
        sensorData.setValue(data.getValue());
        sensorData.setTimestamp(data.getTimestamp() != null ? data.getTimestamp() : LocalDateTime.now());

        final SensorData savedSensorData = sensorDataRepository.save(sensorData);

        trimDbOnLimitExceed(savedSensorData.getSensor());

        return savedSensorData;
    }

    public List<SensorData> addAllSensorData(List<SensorData> sensorDataList) {
        return sensorDataList.stream().map(this::addSensorData).toList();
    }

    public List<SensorData> getAllSensorData() {
        return sensorDataRepository.findAll();
    }

    public List<SensorData> getAllSensorDataBySensorType(Long sensorId) {
        return sensorDataRepository.getAllBySensor_Id(sensorId);
    }

    private Sensor getSensor(SensorData data) {
        return Optional.ofNullable(data.getSensor())
                .map(Sensor::getType)
                .map(sensorRepository::findByType)
                .orElseGet(() -> {
                    Sensor newSensor = new Sensor();
                    newSensor.setType(data.getSensor().getType());
                    newSensor.setDescription("Auto-created newSensor for " + data.getSensor().getType());
                    newSensor = sensorRepository.save(newSensor);
                    SensorSettings defaultSettings = SensorSettings.createDefaultSettings(defaultMaxRecords);
                    defaultSettings.setSensor(newSensor);
                    sensorSettingsRepository.save(defaultSettings);
                    return newSensor;
                });

    }

    private void trimDbOnLimitExceed(Sensor sensor) {
        long count = sensorDataRepository.countBySensor_Id(sensor.getId());
        long MAX_RECORDS = Optional.ofNullable(sensorSettingsRepository.findBySensor(sensor))
                .map(SensorSettings::getMaxRecordsStored)
                .orElse(defaultMaxRecords);  // Keep only the latest 1000 records

        if (count > MAX_RECORDS) {
            sensorDataRepository.deleteOldestEntries(count - MAX_RECORDS, sensor.getId());
        }
    }

    public List<SensorData> getSensorDataByTimeRange(Long sensorId, String fromTime, String toTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime from = LocalDateTime.parse(fromTime, formatter);
        LocalDateTime to = LocalDateTime.parse(toTime, formatter);
        return sensorDataRepository.getAllBySensor_IdAndTimestampBetween(sensorId, from, to);
    }

    public void deleteAllSensorDataBySensorType(Long sensorId) {
        sensorDataRepository.deleteAllBySensor_Id(sensorId);
    }

    public List<SensorData> getAllSensorDataForLastMinutes(Long minutes) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime from = now.minusMinutes(minutes);
        return sensorDataRepository.getAllByTimestampBetween(from, now);
    }
}
