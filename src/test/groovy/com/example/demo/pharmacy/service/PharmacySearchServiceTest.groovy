package com.example.demo.pharmacy.service

import com.example.demo.pharmacy.dto.PharmacyDto
import com.example.demo.pharmacy.entity.Pharmacy
import com.google.common.collect.Lists
import spock.lang.Specification
import spock.lang.Subject

class PharmacySearchServiceTest extends Specification {

    @Subject
    private PharmacySearchService pharmacySearchService

    private PharmacyRepositoryService pharmacyRepositoryService = Mock()

    private List<Pharmacy> pharmacyList

    def setup() {
        pharmacySearchService = new PharmacySearchService(pharmacyRepositoryService)

        pharmacyList = Lists.newArrayList(
                Pharmacy.builder()
                        .id(1L)
                        .pharmacyName("호수온누리약국")
                        .latitude(37.60894036)
                        .longitude(127.029052)
                        .build(),
                Pharmacy.builder()
                        .id(2L)
                        .pharmacyName("돌곶이온누리약국")
                        .latitude(37.61040424)
                        .longitude(127.0569046)
                        .build()
        )
    }

    def "searchPharmacyDtoList"() {
        when:
        pharmacyRepositoryService.findAll() >> pharmacyList
        def result = pharmacySearchService.searchPharmacyDtoList()

        then:
        result.size() == 2
        result.get(0).getId() == 1
        result.get(0).getPharmacyName() == "호수온누리약국"
        result.get(1).getId() == 2
        result.get(1).getPharmacyName() == "돌곶이온누리약국"
    }

//    def "searchPharmacyList 입력 위, 경도 기준으로 거리순 정렬이 되는지 확인"() {
//
//        given:
//        pharmacyList.add(
//                Pharmacy.builder()
//                        .id(3L)
//                        .pharmacyName("홀리데이약국")
//                        .latitude(37.60101417)
//                        .longitude(127.0331664)
//                        .build()
//        )
//
//        // 서울 성북구 종암로10길
//        double inputLatitude = 37.5960650456809
//        double inputLongitude = 127.037033003036
//
//        when:
//        pharmacyRepositoryService.findAll() >> pharmacyList
//
//        def results =
//                pharmacySearchService.searchPharmacyList(inputLatitude, inputLongitude)
//
//        then:
//        results.size() == 3
//        results.get(0).getFirst().getId() == 3
//        results.get(1).getFirst().getId() == 1
//        results.get(2).getFirst().getId() == 2
//
//        String.format("%.1f", results.get(0).getSecond()) == "0.6"
//        String.format("%.1f", results.get(1).getSecond()) == "1.6"
//        String.format("%.1f", results.get(2).getSecond()) == "2.4"
//    }
//
//    def "searchPharmacyList 정해진 반경 10km 내에 검색이 되는지 확인"() {
//        given:
//        pharmacyList.add(
//                Pharmacy.builder()
//                        .id(3L)
//                        .pharmacyName("경기약국")
//                        .latitude(37.3825107393401)
//                        .longitude(127.236707811313)
//                        .build()
//        )
//
//        // 서울 성북구 종암로10길
//        double inputLatitude = 37.5960650456809
//        double inputLongitude = 127.037033003036
//
//        when:
//        pharmacyRepositoryService.findAll() >> pharmacyList
//
//        def results =
//                pharmacySearchService.searchPharmacyList(inputLatitude, inputLongitude)
//
//        then:
//        results.size() == 2
//        results.get(0).getFirst().getId() == 1
//        results.get(1).getFirst().getId() == 2
//    }
}
