package com.example.demo.api.service;

import com.example.demo.api.dto.KakaoApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.net.URI;
import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoAddressSearchService {

    private final KakaoUriBuilderService kakaoUriBuilderService;

    private final RestTemplate restTemplate;
    private final WebClient webClient;

    @Value("${kakao.rest.api.key}")
    private String kakaoRestApiKey;

    public KakaoApiResponseDto requestAddressSearch(String address) {

        if(StringUtils.isEmpty(address)) return null;

        URI uri = kakaoUriBuilderService.buildUriByAddressSearch(address);

        return webClient.get()
                .uri(uri)
                .header(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoRestApiKey)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(KakaoApiResponseDto.class)
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .doOnSuccess(res -> log.info(res.toString()))
                .doOnError(err -> log.error(err.toString()))
                .block();
    }



//    @Retryable(
//            value = {Exception.class},
//            maxAttempts = 2,
//            backoff = @Backoff(delay = 2000)
//    )
//    public KakaoApiResponseDto requestAddressSearch(String address) {
//
//        if(StringUtils.isEmpty(address)) return null;
//
//        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_SEARCH_ADDRESS_URL);
//        uriBuilder.queryParam("query", address);
//
//        URI uri = uriBuilder.build().encode().toUri();
//        log.info("[KakaoAddressSearchService requestAddressSearch] address: {}, uri: {}", address, uri);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoRestApiKey);
//        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
//
//        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, KakaoApiResponseDto.class).getBody();
//    }

//    @Recover
//    public KakaoApiResponseDto recover(Exception e, String address) {
//        log.error("All the retries failed. address: {}, error : {}", address, e.getMessage());
//        return null;
//    }
}
