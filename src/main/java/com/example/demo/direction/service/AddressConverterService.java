package com.example.demo.direction.service;

import com.example.demo.api.dto.DocumentDto;
import com.example.demo.api.dto.KakaoApiResponseDto;
import com.example.demo.api.service.KakaoAddressSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressConverterService {

    private final RetryTemplate retryTemplate;

    private final KakaoAddressSearchService kakaoAddressSearchService;

    public Optional<DocumentDto> convertAddressToGeospatialData(String address)  {

        KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);

        // retryTemplate 사용하는 예제
        //KakaoApiResponseDto kakaoApiResponseDto = retryTemplate.execute(context -> AddressConverterService.this.requestKakaoApi(address));

        List<DocumentDto> documentList =
                Optional.ofNullable(kakaoApiResponseDto)
                        .map(KakaoApiResponseDto::getDocumentList)
                        .orElse(Collections.emptyList());

        return documentList.stream().findFirst();
    }
}
