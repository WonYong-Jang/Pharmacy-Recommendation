package com.example.demo.api;

import com.example.demo.pharmacy.entity.Pharmacy;
import com.example.demo.pharmacy.service.PharmacyService;
import com.example.demo.util.CsvUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PharmacyController {

    private final PharmacyService pharmacyService;

    @GetMapping("/csv/pharmacy/save")
    public String saveCsvToDatabase() {

        List<Pharmacy> pharmacyList = CsvUtils.convertToPharmacyDtoList()
                .stream().map(pharmacyDto ->
                        Pharmacy.builder()
                                .pharmacyName(pharmacyDto.getPharmacyName())
                                .pharmacyAddress(pharmacyDto.getPharmacyAddress())
                                .latitude(pharmacyDto.getLatitude())
                                .longitude(pharmacyDto.getLongitude())
                                .build())
                .collect(Collectors.toList());

        pharmacyService.saveAll(pharmacyList);

        return "success";
    }
}
