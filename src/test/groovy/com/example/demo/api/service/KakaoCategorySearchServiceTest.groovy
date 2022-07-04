package com.example.demo.api.service

import com.example.demo.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired

class KakaoCategorySearchServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    KakaoCategorySearchService kakaoCategorySearchService

    def "test"() {
        given:
        double x = 127.037033003036
        double y = 37.5960650456809
        double radius = 20.0

        when:
        def result = kakaoCategorySearchService.requestCategorySearch(y, x, radius)

        then:
        result.getDocumentList().forEach(t -> {
            println t.getAddressName()
            println t.getDistance()
            println t.getPlaceName()
        })
    }
}
