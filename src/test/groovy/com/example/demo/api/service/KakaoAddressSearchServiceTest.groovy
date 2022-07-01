package com.example.demo.api.service

import com.example.demo.AbstractIntegrationContainerBaseTest
import com.example.demo.api.dto.KakaoApiResponseDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.SpringSpy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.web.client.RestTemplate

import static org.springframework.test.web.client.ExpectedCount.times
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import static org.springframework.test.web.client.response.MockRestResponseCreators.withException
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

class KakaoAddressSearchServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService

    @SpringSpy
    private RestTemplate restTemplate

    private MockRestServiceServer mockServer
    private ObjectMapper mapper = new ObjectMapper()
    private String baseUrl = "https://dapi.kakao.com/v2/local/search/address.json?query="

    def setup() {
        mockServer = MockRestServiceServer.createServer(restTemplate)
    }

    def cleanup() {
        mockServer.verify()
    }

    def "Verify that requestAddressSearch method is retry when it failed once "() {
        given:
        ResponseEntity<KakaoApiResponseDto> response = new ResponseEntity<KakaoApiResponseDto>(HttpStatus.OK)
        def address = "address"

        when:

        mockServer.expect(times(1), requestTo(new URI(baseUrl + address)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(request -> {
                    throw new Exception("error")
                })

        mockServer.expect(requestTo(new URI(baseUrl + address)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                        mapper.writeValueAsString(response), MediaType.APPLICATION_JSON)
                )

        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        2 * restTemplate.exchange(_, _, _, _)
        result != null
    }

    def "Verify that requestAddressSearch method is recovery when it failed twice "() {
        given:
        def address = "address"

        when:
        mockServer.expect(times(2), requestTo(new URI(baseUrl + address)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withException(new IOException("error")))

        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        2 * restTemplate.exchange(_, _, _, _)
        result == null
    }
}
