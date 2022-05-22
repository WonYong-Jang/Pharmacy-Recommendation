package com.example.demo.pharmacy.cache

import com.example.demo.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations

class RedisTemplateTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    RedisTemplate redisTemplate;

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
