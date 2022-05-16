package com.example.demo.pharmacy.repository


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

@Testcontainers // 컨테이너 라이프 사이클 관리
@ActiveProfiles("test") // application-test.yml 를 사용
@SpringBootTest
class PharmacyRepositoryTest extends Specification {

    @Autowired
    PharmacyRepository pharmacyRepository;

    // 테스트 마다 컨테이너를 생성 및 삭제 해주면 너무 느리기 때문에
    // 테스트 실행시 컨테이너를 공유 하면서 각 테스트 시작 전 데이터를 비워 준다.
    @Shared
    MariaDBContainer mariaDBContainer = new MariaDBContainer()
            .withDatabaseName("pharmacyRecommendation"); // Database 지정하기

    def setup() {
        pharmacyRepository.deleteAll();
    }

    def "testcontainers test"() {
        expect:
        assert true
    }

    def "testcontainers test2"() {
        expect:
        assert true
    }
}
