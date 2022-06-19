package com.example.demo.pharmacy.service;

import com.example.demo.pharmacy.entity.Pharmacy;
import com.example.demo.util.Pair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PharmacySearchService {

    private static final int MAX_SEARCH_COUNT = 3; // 최대 검색 갯수
    private static final double RADIUS_KM = 10.0; // 반경 10 km

    private final PharmacyRepositoryService pharmacyRepositoryService;

    public List<Pair<Pharmacy, Double>> searchPharmacyList(double targetLatitude, double targetLongitude) {
        return pharmacyRepositoryService.findAll()
                .stream().map(pharmacy ->
                        new Pair<>(pharmacy, calculateDistance(targetLatitude, targetLongitude,
                                pharmacy.getLatitude(), pharmacy.getLongitude())))
                .filter(pharmacy -> pharmacy.getSecond() <= RADIUS_KM)
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
        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
    }
}
