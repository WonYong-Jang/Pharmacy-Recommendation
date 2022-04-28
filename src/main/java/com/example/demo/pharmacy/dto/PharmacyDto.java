package com.example.demo.pharmacy.dto;

import com.example.demo.pharmacy.entity.Pharmacy;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PharmacyDto {

    private Long id;
    private String pharmacyName;
    private String pharmacyAddress;
    private double latitude;
    private double longitude;
}
