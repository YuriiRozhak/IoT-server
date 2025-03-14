package com.example.iotserver.controller;

import com.example.iotserver.model.SensorData;
import com.example.iotserver.service.SensorDataService;
import com.example.iotserver.service.SensorService;
import com.example.iotserver.service.SensorSettingsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class SensorDataController {
    private final SensorDataService sensorDataService;

    private final SensorSettingsService sensorSettingsService;
    private final SensorService sensorService;


    @PostMapping("/add")
    public SensorData addSensorData(@RequestBody SensorData data) {

        return sensorDataService.addSensorData(data);
    }

    @PostMapping("/add/all")
    public List<SensorData> addListSensorData(@RequestBody List<SensorData> data) {

        return sensorDataService.addAllSensorData(data);
    }

    @GetMapping("/all")
    public List<SensorData> getAllSensorData() {
        return sensorDataService.getAllSensorData();
    }

    @GetMapping("/all/last-minutes")
    public List<SensorData> getAllSensorDataForLastMinutes(@RequestParam(name = "time") Long time) {
        return sensorDataService.getAllSensorDataForLastMinutes(time);
    }

    @GetMapping("/all/{sensorId}")
    public List<SensorData> getAllSensorDataBySensorType(@PathVariable(name = "sensorId") Long sensorId) {
        return sensorDataService.getAllSensorDataBySensorType(sensorId);
    }

    @GetMapping("/all/{sensorId}/range")
    public List<SensorData> getSensorDataByTimeRange(
            @PathVariable(name = "sensorId") Long sensorId,
            @RequestParam(name = "from") String fromTime,
            @RequestParam(name = "to", required = false) String toTime) {
        return sensorDataService.getSensorDataByTimeRange(sensorId, fromTime, toTime);
    }

    @DeleteMapping("/all/{sensorId}")
    @Transactional
    public ResponseEntity<Void> deleteAllSensorDataBySensorType(@PathVariable(name = "sensorId") Long sensorId) {
        sensorDataService.deleteAllSensorDataBySensorType(sensorId);
        sensorSettingsService.deleteBySensorId(sensorId);
        sensorService.deleteBySensorId(sensorId);
        return new ResponseEntity<Void>(HttpStatusCode.valueOf(200));
    }



}
