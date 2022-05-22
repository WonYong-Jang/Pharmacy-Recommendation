package com.example.demo.pharmacy.repository

import com.example.demo.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired

class PharmacyRepositoryTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    PharmacyRepository pharmacyRepository

    def setup() {
        pharmacyRepository.deleteAll()
    }

    def "testcontainers test"() {
        expect:
        assert true
    }

    def "testcontainers test2"() {
        expect:
        assert true
    }

    def "testcontainers test3"() {
        expect:
        assert true
    }
}
