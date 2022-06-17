package com.example.demo.pharmacy.service;

import com.example.demo.direction.dto.DocumentDto;
import com.example.demo.direction.dto.OutputDto;
import com.example.demo.direction.entity.Direction;
import com.example.demo.direction.service.AddressConverterService;
import com.example.demo.direction.service.DirectionService;
import com.example.demo.pharmacy.entity.Pharmacy;
import com.example.demo.util.Pair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PharmacyRecommendationService {

    private static final String ROAD_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";

    private final AddressConverterService addressConverterService;
    private final DirectionService directionService;
    private final PharmacySearchService pharmacySearchService;

    @Value("${pharmacy.recommendation.base.url}")
    private String baseUrl;

    public List<OutputDto> recommendPharmacyList(String address) {

        DocumentDto documentDto = addressConverterService.convertAddressToGeospatialData(address)
                .orElse(null);

        if(Objects.isNull(documentDto)) {
            log.error("PharmacyRecommendationService.recommendPharmacyList fail. Input address: {}", address);
            return Collections.emptyList();
        }

        double inputLatitude = documentDto.getLatitude();
        double inputLongitude = documentDto.getLongitude();

        List<Pair<Pharmacy, Double>> resultPairs =
                pharmacySearchService.searchPharmacyList(inputLatitude, inputLongitude);

        List<Direction> directionList = convertToDirectionList(resultPairs);

        return directionService.saveAll(directionList)
                .stream().map(direction ->
                        OutputDto.builder()
                                .pharmacyName(direction.getTargetPharmacyName())
                                .pharmacyAddress(direction.getTargetAddress())
                                .directionUrl(baseUrl + directionService.encodeDirectionId(direction.getId()))
                                .roadViewUrl(ROAD_VIEW_BASE_URL + direction.getTargetLatitude()+","+direction.getTargetLongitude())
                                .build())
                .collect(Collectors.toList());
    }


    private List<Direction> convertToDirectionList(List<Pair<Pharmacy, Double>> resultPairs) {

        return resultPairs.stream().map(pair -> {
            Pharmacy pharmacy = pair.getFirst();
            return Direction.builder()
                    .targetPharmacyName(pharmacy.getPharmacyName())
                    .targetAddress(pharmacy.getPharmacyAddress())
                    .targetLatitude(pharmacy.getLatitude())
                    .targetLongitude(pharmacy.getLongitude())
                    .build();
        }).collect(Collectors.toList());
    }
}
