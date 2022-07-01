package com.example.demo.direction.service

import com.example.demo.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired

class AddressConverterServiceIntegrationTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    AddressConverterService addressConverterService

    def "convertAddressToGeospatialData according to multiple parameters"() {

        expect:
        result == addressConverterService.convertAddressToGeospatialData(address).isPresent()

        where:
        address                                 | result
        "서울 특별시 성북구 종암동"                   | true
        "서울 성북구 종암동 91"                     | true
        "서울 성북구 종암동"                        | true
        "서울 성북구 종암동 잘못된 주소"               | false
        "광진구 구의동 251-45"                     | true
        "광진구 구의동 251-455555"                 | false
        ""                                      | false
    }
}
