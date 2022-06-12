package com.example.demo.api;

import com.example.demo.pharmacy.cache.PharmacyRedisTemplateService;
import com.example.demo.pharmacy.dto.PharmacyDto;
import com.example.demo.pharmacy.entity.Pharmacy;
import com.example.demo.pharmacy.service.PharmacyService;
import com.example.demo.util.CsvUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PharmacyController {

    private final PharmacyService pharmacyService;
    private final PharmacyRedisTemplateService pharmacyRedisTemplateService;

    @PostConstruct
    public void init() {
        saveCsvToDatabase();
        saveCsvToRedis();
    }

    public String saveCsvToDatabase() {

        List<Pharmacy> pharmacyList = loadPharmacyList();
        pharmacyService.saveAll(pharmacyList);

        return "success";
    }

    public String saveCsvToRedis() {

        List<PharmacyDto> pharmacyDtoList = pharmacyService.findAll()
                .stream().map(pharmacy -> PharmacyDto.builder()
                        .id(pharmacy.getId())
                        .pharmacyName(pharmacy.getPharmacyName())
                        .pharmacyAddress(pharmacy.getPharmacyAddress())
                        .latitude(pharmacy.getLatitude())
                        .longitude(pharmacy.getLongitude())
                        .build()).collect(Collectors.toList());

        pharmacyDtoList.forEach(pharmacyRedisTemplateService::save);

        return "success";
    }

    private List<Pharmacy> loadPharmacyList() {
        return CsvUtils.convertToPharmacyDtoList()
                .stream().map(pharmacyDto ->
                        Pharmacy.builder()
                                .id(pharmacyDto.getId())
                                .pharmacyName(pharmacyDto.getPharmacyName())
                                .pharmacyAddress(pharmacyDto.getPharmacyAddress())
                                .latitude(pharmacyDto.getLatitude())
                                .longitude(pharmacyDto.getLongitude())
                                .build())
                .collect(Collectors.toList());
    }
}
