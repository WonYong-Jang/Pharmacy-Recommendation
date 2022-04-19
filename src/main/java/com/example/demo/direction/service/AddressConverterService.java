package com.example.demo.direction.service;

import com.example.demo.direction.dto.DocumentDto;
import com.example.demo.direction.dto.KakaoApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressConverterService {

    private String kakaoRestApiKey = "0a4d664f9907b3da6d10f6ea19bcfec7";
    private static final String KAKAO_LOCAL_SEARCH_ADDRESS_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    public Optional<DocumentDto> convertAddressToGeospatialData(String address) {

        // address 유효 check

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_SEARCH_ADDRESS_URL);
        uriBuilder.queryParam("query", address);

        URI uri = uriBuilder.build().encode().toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoRestApiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        KakaoApiResponseDto kakaoApiResponseDto = restTemplate.exchange(uri, HttpMethod.GET, entity, KakaoApiResponseDto.class)
                .getBody();


        List<DocumentDto> documentList = Optional.ofNullable(kakaoApiResponseDto)
                .map(KakaoApiResponseDto::getDocumentList)
                .orElse(Collections.emptyList());

        return documentList.stream().findFirst();
    }
}
