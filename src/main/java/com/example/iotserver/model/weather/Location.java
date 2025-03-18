package com.example.iotserver.model.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Location {

    private String name;
    private String region;
    private String country;
    private double lat;
    private double lon;

    @JsonProperty("tz_id")
    private String timezone;

    @JsonProperty("localtime_epoch")
    private long localtimeEpoch;

    private String localtime;

    // Getters and Setters
}
