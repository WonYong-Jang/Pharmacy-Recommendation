package com.example.demo.pharmacy.cache

import com.example.demo.pharmacy.dto.PharmacyDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.GenericContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest
class PharmacyRedisTemplateServiceTest extends Specification {

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    PharmacyRedisTemplateService pharmacyRedisTemplateService;

    @Shared
    GenericContainer redis = new GenericContainer<>("redis:6-alpine")
            .withExposedPorts(6379)

    def setupSpec() {
        System.setProperty("spring.redis.host", redis.getHost())
        System.setProperty("spring.redis.port", redis.getMappedPort(6379) + "")
    }

    def setup() {
        println "컨테이너 로그 확인 : " + redis.getLogs()
    }

    def "RedisTemplate test"() {
        given:
        ValueOperations ops = redisTemplate.opsForValue()

        when:
        ops.set("key", "hello")
        String result = ops.get("key")

        then:
        result == "hello"
    }

    def "save"() {
        given:
        String pharmacyName = "name"
        String pharmacyAddress = "address"
        PharmacyDto dto =
                PharmacyDto.builder()
                        .id(1L)
                        .pharmacyName(pharmacyName)
                        .pharmacyAddress(pharmacyAddress)
                        .build()

        when:
        pharmacyRedisTemplateService.save(dto)
        List<PharmacyDto> result = pharmacyRedisTemplateService.findAll()

        then:
        result.size() == 1
        result.get(0).id == 1L
        result.get(0).pharmacyName == pharmacyName
        result.get(0).pharmacyAddress == pharmacyAddress
    }

//    @Unroll
//    def "save if required value is null"() {
//        // where
//    }
}
