package com.example.demo.pharmacy.cache

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.GenericContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest
class RedisTemplateTest extends Specification {

    @Autowired
    RedisTemplate redisTemplate;

    // @Shared starts container once before all test methods
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

    def "RedisTemplate String operations"() {
        given:
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue()
        String key = "stringKey"
        String value = "hello"

        when:
        valueOperations.set(key, value)

        then:
        String resultValue = valueOperations.get(key)
        resultValue == "hello"
    }
}
