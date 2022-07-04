package com.example.demo.pharmacy.service;

import com.example.demo.api.dto.DocumentDto;
import com.example.demo.direction.dto.OutputDto;
import com.example.demo.direction.entity.Direction;
import com.example.demo.direction.service.AddressConverterService;
import com.example.demo.direction.service.Base62Service;
import com.example.demo.direction.service.DirectionService;
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

    private static final int MAX_SEARCH_COUNT = 3; // 최대 검색 갯수
    private static final double RADIUS_KM = 10.0; // 반경 10 km
    private static final String ROAD_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";

    private final AddressConverterService addressConverterService;
    private final DirectionService directionService;
    private final Base62Service base62Service;

    @Value("${pharmacy.recommendation.base.url}")
    private String baseUrl;

    public List<OutputDto> recommendPharmacyList(String address) {

        DocumentDto documentDto = addressConverterService.convertAddressToGeospatialData(address)
                .orElse(null);

        if (Objects.isNull(documentDto)) {
            log.error("[PharmacyRecommendationService.recommendPharmacyList fail] Input address: {}", address);
            return Collections.emptyList();
        }

        List<Direction> directionList = directionService.buildDirectionList(documentDto)
                .stream()
                .filter(direction -> direction.getDistance() <= RADIUS_KM)
                .sorted(Comparator.comparing(Direction::getDistance))
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());

        return directionService.saveAll(directionList)
                .stream()
                .map(this::convertToOutputDto)
                .collect(Collectors.toList());
    }

    private OutputDto convertToOutputDto(Direction direction) {

        return OutputDto.builder()
                .pharmacyName(direction.getTargetPharmacyName())
                .pharmacyAddress(direction.getTargetAddress())
                .directionUrl(baseUrl + base62Service.encodeDirectionId(direction.getId()))
                .roadViewUrl(ROAD_VIEW_BASE_URL + direction.getTargetLatitude() + "," + direction.getTargetLongitude())
                .distance(String.format("%.2f km", direction.getDistance()))
                .build();
    }
}