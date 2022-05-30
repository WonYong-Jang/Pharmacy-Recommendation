package com.example.demo.direction.service;

import com.example.demo.direction.dto.DocumentDto;
import com.example.demo.direction.dto.KakaoApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AddressConverterService {

    private final RetryTemplate retryTemplate;

    @Value("${kakao.rest.api.key}")
    private String kakaoRestApiKey;

    private static final String KAKAO_LOCAL_SEARCH_ADDRESS_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    private static final Pattern ADDRESS_PATTERN = Pattern.compile("(([가-힣]+(d|d(,|.)d|)+(읍|면|동|가|리))(^구|)((d(~|-)d|d)(가|리|)|))([ ](산(d(~|-)d|d))|)|\n" +
            "(([가-힣]|(d(~|-)d)|d)+(로|길))");

    public Optional<DocumentDto> convertAddressToGeospatialData(String address) {

        // address validation check
        Matcher matcher = ADDRESS_PATTERN.matcher(address);
        if(!matcher.find()) return Optional.empty();

        //KakaoApiResponseDto kakaoApiResponseDto = requestKakaoApi(address);
        KakaoApiResponseDto kakaoApiResponseDto = retryTemplate.execute(context -> requestKakaoApi(address));

        List<DocumentDto> documentList = Optional.ofNullable(kakaoApiResponseDto)
                .map(KakaoApiResponseDto::getDocumentList)
                .orElse(Collections.emptyList());

        return documentList.stream().findFirst();
    }

    private KakaoApiResponseDto requestKakaoApi(String address) {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_SEARCH_ADDRESS_URL);
        uriBuilder.queryParam("query", address);

        URI uri = uriBuilder.build().encode().toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoRestApiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);


        RestTemplate restTemplate = new RestTemplate();
        KakaoApiResponseDto kakaoApiResponseDto = restTemplate.exchange(uri, HttpMethod.GET, entity, KakaoApiResponseDto.class)
                .getBody();
        return kakaoApiResponseDto;
    }
}
