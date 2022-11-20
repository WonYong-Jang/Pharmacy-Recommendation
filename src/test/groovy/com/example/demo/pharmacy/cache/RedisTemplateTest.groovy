package com.example.demo.pharmacy.cache

import com.example.demo.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.SetOperations
import org.springframework.data.redis.core.ValueOperations

class RedisTemplateTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private RedisTemplate redisTemplate

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

    def "RedisTemplate sort operations"() {
        given:
        SetOperations<String, String> setOperations = redisTemplate.opsForSet()
        String key = "setKey"
        when:

        setOperations.add(key, "h", "e", "l", "l", "o")

        Set<String> members = setOperations.members(key)
        Long size = setOperations.size(key)

        then:

        members.containsAll(["h","e","l","o"])
        size == 4
    }

    def "RedisTemplate hash operations"() {
        given:
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash()
        String key = "hashKey"

        when:
        hashOperations.put(key, "subKey", "value")

        then:
        String value = hashOperations.get(key, "subKey")
        value == "value"

        Map<String, String> entries = hashOperations.entries(key)
        entries.keySet().contains("subKey")
        entries.values().contains("value")

        Long size = hashOperations.size(key)
        size == entries.size()
    }
}
