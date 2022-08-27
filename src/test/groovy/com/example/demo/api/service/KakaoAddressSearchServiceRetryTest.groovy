package com.example.demo.api.service

import com.example.demo.AbstractIntegrationContainerBaseTest
import com.example.demo.api.dto.DocumentDto
import com.example.demo.api.dto.KakaoApiResponseDto
import com.example.demo.api.dto.MetaDto
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import reactor.core.Exceptions

class KakaoAddressSearchServiceRetryTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService

    @SpringBean
    private KakaoUriBuilderService kakaoUriBuilderService = Mock()

    private MockWebServer mockWebServer

    private ObjectMapper mapper = new ObjectMapper()

    def setup() {
        mockWebServer = new MockWebServer()
        mockWebServer.start()
    }

    def cleanup() {
        mockWebServer.shutdown()
    }

    def "requestAddressSearch retry success"() {
        given:
        def address = "서울 성북구 종암로 10길"
        def metaDto = new MetaDto(1)
        def documentDto = DocumentDto.builder()
                .addressName(address)
                .build()
        def expectedResponse = new KakaoApiResponseDto(metaDto, Arrays.asList(documentDto))
        def uri = mockWebServer.url("/").uri()

        when:
        kakaoUriBuilderService.buildUriByAddressSearch(address) >> uri

        mockWebServer.enqueue(new MockResponse().setResponseCode(429))
        mockWebServer.enqueue(new MockResponse().setResponseCode(429))
        mockWebServer.enqueue(new MockResponse().setResponseCode(429))
        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .setBody(mapper.writeValueAsString(expectedResponse))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))

        def kakaoApiResult = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        def takeRequest = mockWebServer.takeRequest()
        takeRequest.getMethod() == "GET"
        kakaoApiResult.getDocumentList().size() == 1
        kakaoApiResult.getMetaDto().totalCount == 1
        kakaoApiResult.getDocumentList().get(0).getAddressName() == address
    }

    def "requestAddressSearch retry fail "() {
        given:
        def address = "서울 성북구 종암로 10길"
        def uri = mockWebServer.url("/").uri()

        when:
        kakaoUriBuilderService.buildUriByAddressSearch(address) >> uri

        mockWebServer.enqueue(new MockResponse().setResponseCode(429))
        mockWebServer.enqueue(new MockResponse().setResponseCode(429))
        mockWebServer.enqueue(new MockResponse().setResponseCode(429))
        mockWebServer.enqueue(new MockResponse().setResponseCode(429))

        kakaoAddressSearchService.requestAddressSearch(address)

        then:
        def e = thrown(Exceptions.RetryExhaustedException.class)
    }
}