package com.example.demo.pharmacy.dto;

import com.example.demo.pharmacy.entity.Pharmacy;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class PharmacyDto implements Serializable { // todo Serializable DTO?

    private Long id;
    private String pharmacyName;
    private String pharmacyAddress;
    private double latitude;
    private double longitude;
}
