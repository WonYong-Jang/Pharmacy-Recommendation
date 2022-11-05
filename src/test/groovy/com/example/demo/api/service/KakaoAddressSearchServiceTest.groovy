package com.example.demo.api.service

import com.example.demo.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired

class KakaoAddressSearchServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService

    def "address 파라미터 값이 null이면, requestAddressSearch 메소드는 null을 리턴한다."() {
        given:
        def address = null

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result == null
    }

    def "주소값이 valid하다면, requestAddressSearch 메소드는 정상적으로 document를 반환한다."() {
        given:
        def address = "서울 성북구 종암로 10길"

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result.documentList.size() > 0
        result.metaDto.totalCount > 0
        result.documentList.get(0).addressName != null
    }


    def "정상적인 주소를 입력했을 경우, 정상적으로 위도 경도로 변환 된다."() {

        given:
        boolean actualResult = false

        when:
        def searchResult = kakaoAddressSearchService.requestAddressSearch(inputAddress)

        then:
        if(searchResult == null) actualResult = false
        else actualResult = searchResult.getDocumentList().size() > 0

        actualResult == expectedResult

        where:
        inputAddress                            | expectedResult
        "서울 특별시 성북구 종암동"                   | true
        "서울 성북구 종암동 91"                     | true
        "서울 대학로"                             | true
        "서울 성북구 종암동 잘못된 주소"               | false
        "광진구 구의동 251-45"                     | true
        "광진구 구의동 251-455555"                 | false
        ""                                      | false
    }
}