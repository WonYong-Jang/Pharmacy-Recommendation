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

    private static final Integer MAX_SEARCH_COUNT = 3;
    private final AddressConverterService addressConverterService;
    private final PharmacyService pharmacyService;
    private final DirectionService directionService;

    @Value("${pharmacy.recommendation.base.url}")
    private String baseUrl;

    public List<OutputDto> recommendPharmacyList(String address) {

        DocumentDto documentDto = addressConverterService.convertAddressToGeospatialData(address)
                .orElse(null);

        if(Objects.isNull(documentDto)) {
            log.error("PharmacyRecommendationService.recommendPharmacyList fail. Input address: {}", address);
            return Collections.emptyList();
        }

        String inputAddress = documentDto.getAddressName();
        double inputLatitude = documentDto.getLatitude();
        double inputLongitude = documentDto.getLongitude();

        List<Pair<Pharmacy, Double>> resultPairs = searchPharmacyList(inputLatitude, inputLongitude);

        List<Direction> directionList = convertToDirectionList(inputAddress, inputLatitude, inputLongitude, resultPairs);

        return directionService.saveAll(directionList)
                .stream().map(direction ->
                        OutputDto.builder()
                                .pharmacyName(direction.getTargetPharmacyName())
                                .pharmacyAddress(direction.getTargetAddress())
                                .directionUrl(baseUrl + directionService.encodeDirectionId(direction.getId()))
                                .build())
                .collect(Collectors.toList());
    }

    private List<Direction> convertToDirectionList(String inputAddress, double inputLatitude, double inputLongitude, List<Pair<Pharmacy, Double>> resultPairs) {

        return resultPairs.stream().map(pair -> {
            Pharmacy pharmacy = pair.getFirst();
            return Direction.builder()
                    .inputAddress(inputAddress)
                    .inputLatitude(inputLatitude)
                    .inputLongitude(inputLongitude)
                    .targetPharmacyName(pharmacy.getPharmacyName())
                    .targetAddress(pharmacy.getPharmacyAddress())
                    .targetLatitude(pharmacy.getLatitude())
                    .targetLongitude(pharmacy.getLongitude())
                    .build();
        }).collect(Collectors.toList());
    }


    private List<Pair<Pharmacy, Double>> searchPharmacyList(double targetLatitude, double targetLongitude) {
        return pharmacyService.findAll()
                .stream().map(pharmacy ->
                        new Pair<>(pharmacy, calculateDistance(targetLatitude, targetLongitude,
                                pharmacy.getLatitude(), pharmacy.getLongitude())))
                .sorted(Comparator.comparing(Pair::getSecond))
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());
    }


    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371; //Kilometers
        return earthRadius * Math.acos(Math.sin(lat1)*Math.sin(lat2) + Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon1 - lon2));
    }
}
