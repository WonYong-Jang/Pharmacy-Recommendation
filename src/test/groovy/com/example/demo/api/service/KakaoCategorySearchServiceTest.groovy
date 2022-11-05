package com.example.demo.api.service

import com.example.demo.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired

import java.util.stream.Collectors

class KakaoCategorySearchServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    KakaoCategorySearchService kakaoCategorySearchService

    def "requestCategorySearch sort by distance"() {
        given:
        double x = 127.037033003036 // longitude
        double y = 37.5960650456809 // latitude
        double radius = 10.0 // km

        when:
        def result = kakaoCategorySearchService.requestPharmacyCategorySearch(y, x, radius)

        def inputList = result.getDocumentList().stream()
                .map(t -> t.getDistance())
                .collect(Collectors.toList())
        def outputList = inputList.stream()
                .sorted()
                .collect(Collectors.toList())

        then:
        inputList == outputList
        inputList.size() == outputList.size()
        assert result.getDocumentList().every() {
            it.getDistance() <= radius * 1000
        }
    }
}
