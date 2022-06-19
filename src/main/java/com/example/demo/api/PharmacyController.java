package com.example.demo.api;

import com.example.demo.pharmacy.cache.PharmacyRedisTemplateService;
import com.example.demo.pharmacy.dto.PharmacyDto;
import com.example.demo.pharmacy.entity.Pharmacy;
import com.example.demo.pharmacy.service.PharmacyRepositoryService;
import com.example.demo.util.CsvUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PharmacyController {

    private final PharmacyRepositoryService pharmacyRepositoryService;
    private final PharmacyRedisTemplateService pharmacyRedisTemplateService;

    @PostConstruct
    public void init() {
        saveCsvToDatabase();
        saveCsvToRedis();
    }

    public void saveCsvToDatabase() {

        List<Pharmacy> pharmacyList = loadPharmacyList();
        pharmacyRepositoryService.saveAll(pharmacyList);

    }

    public void saveCsvToRedis() {

        List<PharmacyDto> pharmacyDtoList = pharmacyRepositoryService.findAll()
                .stream().map(pharmacy -> PharmacyDto.builder()
                        .id(pharmacy.getId())
                        .pharmacyName(pharmacy.getPharmacyName())
                        .pharmacyAddress(pharmacy.getPharmacyAddress())
                        .latitude(pharmacy.getLatitude())
                        .longitude(pharmacy.getLongitude())
                        .build()).collect(Collectors.toList());

        pharmacyDtoList.forEach(pharmacyRedisTemplateService::save);

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
