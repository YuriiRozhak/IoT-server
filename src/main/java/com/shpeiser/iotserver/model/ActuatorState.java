package com.shpeiser.iotserver.model;

public enum ActuatorState {
    ON,
    OFF;

    @Override
    public String toString() {
        return name();
    }
}
