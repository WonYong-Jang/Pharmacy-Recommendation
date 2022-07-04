package com.example.demo.direction.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DirectionDto {

    private String inputAddress;
    private double inputLatitude;
    private double inputLongitude;

    private String targetPharmacyName;
    private String targetAddress;
    private double targetLatitude;
    private double targetLongitude;

    private double distance;
}
