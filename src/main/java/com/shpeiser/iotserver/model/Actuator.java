package com.shpeiser.iotserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Actuator {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;        // "light", "fan", "buzzer"
    private String description; // Optional details about the rule
    private short pinIn;          // GPIO Input pin number
    private short pinOut;          // GPIO Output pin number
    private boolean state;// true or false
    private boolean scenarioState;// true or false


}
