package com.example.iotserver.model.weather;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Condition {

    private String text;
    private String icon;
    private int code;

}
