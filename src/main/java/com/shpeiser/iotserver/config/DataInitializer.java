package com.shpeiser.iotserver.config;

import com.shpeiser.iotserver.model.Actuator;
import com.shpeiser.iotserver.repository.ActuatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final ActuatorRepository actuatorRepository;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
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
        };
    }
}