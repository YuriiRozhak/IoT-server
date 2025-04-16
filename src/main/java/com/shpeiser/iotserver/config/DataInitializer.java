package com.shpeiser.iotserver.config;

import com.shpeiser.iotserver.model.*;
import com.shpeiser.iotserver.repository.ActuatorRepository;
import com.shpeiser.iotserver.repository.SensorComparatorRepository;
import com.shpeiser.iotserver.repository.SensorRepository;
import com.shpeiser.iotserver.service.ScenarioService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final ActuatorRepository actuatorRepository;
    private final ScenarioService scenarioService;
    private final SensorComparatorRepository sensorComparatorRepository;
    private final SensorRepository sensorRepository;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            initAActuaotors();
            initScenarios();
        };
    }

    @Transactional
    private void initAActuaotors() {
        // Create and save your initial data here
        List<Actuator> all = actuatorRepository.findAll();
        if (all.isEmpty()) {
            Actuator buzzer = Actuator.builder()
                    .id(1L)
                    .name("Buzzer")
                    .description("Buzzer does beeeeep")
                    .build();
            Actuator led = Actuator.builder()
                    .id(2L)
                    .name("LED")
                    .description("LED does blink")
                    .build();
            Actuator fan = Actuator.builder()
                    .id(3L)
                    .name("fan")
                    .description("fan does find")
                    .build();
            actuatorRepository.saveAll(List.of(buzzer, led, fan));
        }
    }

    @Transactional
    private void initScenarios() throws IOException {
        // Create and save your initial data here
        final Scenario ledActivationScenario = new Scenario();
        ledActivationScenario.setName("LED Activation Scenario");
        final Actuator led = actuatorRepository.findById(2L).orElseGet(() -> Actuator.builder()
                .id(2L)
                .name("LED")
                .description("LED does blink")
                .build());
        ledActivationScenario.setActuators(Collections.singletonList(led));
        final SensorComparator lightSensorComparator = new SensorComparator();

        Sensor lightSensor = sensorRepository.findByType("light");
        if (lightSensor == null) {
            Sensor newSensor = new Sensor();
            newSensor.setType("light");
            newSensor.setDescription("Light Sensor");
            lightSensor = sensorRepository.save(newSensor);
        }

        lightSensorComparator.setSensor(lightSensor);
        lightSensorComparator.setComparator("=");
        lightSensorComparator.setValue(0.0);

        final SensorComparator motionSensorComparator = new SensorComparator();

        Sensor motionSensor = sensorRepository.findByType("motion");
        if (motionSensor == null) {
            Sensor newSensor = new Sensor();
            newSensor.setType("motion");
            newSensor.setDescription("motion Sensor");
            motionSensor = sensorRepository.save(newSensor);
        }

        motionSensorComparator.setSensor(motionSensor);
        motionSensorComparator.setComparator("=");
        motionSensorComparator.setValue(1.0);

        ledActivationScenario.setSensorComparators(List.of(lightSensorComparator, motionSensorComparator));

        ledActivationScenario.setConditionType(ConditionType.ALL);
        ledActivationScenario.setActive(true);
        scenarioService.save(ledActivationScenario);
    }
}