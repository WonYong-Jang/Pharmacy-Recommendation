package com.example.demo.direction.service;

import com.example.demo.direction.dto.DocumentDto;
import com.example.demo.direction.dto.KakaoApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressConverterService {

    private final RetryTemplate retryTemplate;
    private final WebClient webClient;

    @Value("${kakao.rest.api.key}")
    private String kakaoRestApiKey;

    private static final String KAKAO_LOCAL_SEARCH_ADDRESS_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    @Retryable(
            value = {RuntimeException.class},
            maxAttempts = 2,
            backoff = @Backoff(delay = 3000)
    )
    public Optional<DocumentDto> convertAddressToGeospatialData(String address) {

        KakaoApiResponseDto kakaoApiResponseDto = requestKakaoApi(address);
        //KakaoApiResponseDto kakaoApiResponseDto = retryTemplate.execute(context -> AddressConverterService.this.requestKakaoApi(address));

        List<DocumentDto> documentList = Optional.ofNullable(kakaoApiResponseDto).map(KakaoApiResponseDto::getDocumentList).orElse(Collections.emptyList());

        return documentList.stream().findFirst();
    }

    private KakaoApiResponseDto requestKakaoApi(String address) {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_SEARCH_ADDRESS_URL);
        uriBuilder.queryParam("query", address);

        URI uri = uriBuilder.build().encode().toUri();
        log.info("AddressConverterService requestKakaoApi. address: {}, uri: {}", address, uri);

        return webClient.get()
                .uri(uri)
                .header(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoRestApiKey)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(KakaoApiResponseDto.class)
                .block();
    }

    @Recover
    public Optional<DocumentDto> recover(RuntimeException e, String address) {
        log.error("All the retries failed. address: {}, error : {}", address, e);
        return Optional.empty();
    }
}
