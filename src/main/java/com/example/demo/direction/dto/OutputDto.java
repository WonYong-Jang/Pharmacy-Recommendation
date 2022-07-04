package com.example.demo.direction.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OutputDto {

    private String pharmacyName;
    private String pharmacyAddress;
    private String directionUrl;
    private String roadViewUrl;
    private String distance;
}
