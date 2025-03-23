package com.shpeiser.iotserver.controller;

import com.shpeiser.iotserver.model.SensorData;
import com.shpeiser.iotserver.service.ScenarioService;
import com.shpeiser.iotserver.service.SensorDataService;
import com.shpeiser.iotserver.service.SensorService;
import com.shpeiser.iotserver.service.SensorSettingsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class SensorDataController {
    private final SensorDataService sensorDataService;

    private final SensorSettingsService sensorSettingsService;
    private final SensorService sensorService;
    private final ScenarioService scenarioService;


    @PostMapping("/add")
    public SensorData addSensorData(@RequestBody SensorData data) {
        SensorData sensorData = sensorDataService.addSensorData(data);
        scenarioService.checkAllScenarios(Collections.singleton(sensorData));
        return sensorData;
    }

    @PostMapping("/add/all")
    public List<SensorData> addListSensorData(@RequestBody List<SensorData> data) {
        List<SensorData> sensorData = sensorDataService.addAllSensorData(data);
        scenarioService.checkAllScenarios(sensorData);
        return sensorData;
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
