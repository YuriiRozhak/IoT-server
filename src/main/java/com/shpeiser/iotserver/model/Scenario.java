package com.shpeiser.iotserver.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Scenario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;        // "light", "fan", "buzzer"
    private String description; // Optional details about the rule

    @OneToMany
    @JoinColumn(name = "scenario_id")
    private List<Actuator> actuators;

    @OneToMany
    @JoinColumn(name = "scenario_id")
    private List<SensorComparator> sensorComparators;

    private ConditionType conditionType;


    private boolean active;
}