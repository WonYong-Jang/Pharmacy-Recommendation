package com.example.demo.api.service;

import com.example.demo.api.dto.KakaoApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoAddressSearchService {

    private final RestTemplate restTemplate;

    @Value("${kakao.rest.api.key}")
    private String kakaoRestApiKey;

    private static final String KAKAO_LOCAL_SEARCH_ADDRESS_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    @Retryable(
            value = {Exception.class},
            maxAttempts = 2,
            backoff = @Backoff(delay = 2000)
    )
    public KakaoApiResponseDto requestAddressSearch(String address) {

        if(StringUtils.isEmpty(address)) return null;

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_SEARCH_ADDRESS_URL);
        uriBuilder.queryParam("query", address);

        URI uri = uriBuilder.build().encode().toUri();
        log.info("[KakaoAddressSearchService requestAddressSearch] address: {}, uri: {}", address, uri);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoRestApiKey);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, KakaoApiResponseDto.class).getBody();
    }

    @Recover
    public KakaoApiResponseDto recover(Exception e, String address) {
        log.error("All the retries failed. address: {}, error : {}", address, e.getMessage());
        return null;
    }
}
