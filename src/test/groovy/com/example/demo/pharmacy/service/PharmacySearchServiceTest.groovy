package com.example.demo.pharmacy.service

import com.example.demo.pharmacy.entity.Pharmacy
import com.google.common.collect.Lists
import spock.lang.Specification
import spock.lang.Subject

class PharmacySearchServiceTest extends Specification {

    @Subject
    PharmacySearchService pharmacySearchService

    PharmacyService pharmacyService = Mock()

    List<Pharmacy> pharmacyList;

    def setup() {
        pharmacySearchService = new PharmacySearchService(pharmacyService)

        pharmacyList = Lists.newArrayList(
                Pharmacy.builder()
                        .id(1L)
                        .latitude(37.60894036)
                        .longitude(127.029052)
                        .build(),
                Pharmacy.builder()
                        .id(2L)
                        .latitude(37.61040424)
                        .longitude(127.0569046)
                        .build(),
                Pharmacy.builder()
                        .id(3L)
                        .latitude(37.60101417)
                        .longitude(127.0331664)
                        .build()
        )
    }

    def "searchPharmacyList"() {
        given:
        // 서울 성북구 종암로9길
        double inputLatitude = 37.5961506147082
        double inputLongitude = 127.033179659381

        when:
        pharmacyService.findAll() >> pharmacyList

        def results =
                pharmacySearchService.searchPharmacyList(inputLatitude, inputLongitude)

        then:
        results.size() == 3
        results.get(0).getFirst().getId() == 3
        results.get(1).getFirst().getId() == 1
        results.get(2).getFirst().getId() == 2
    }
}
