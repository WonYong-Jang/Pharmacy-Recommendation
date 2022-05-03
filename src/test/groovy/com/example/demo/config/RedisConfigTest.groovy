package com.example.demo.config

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

class RedisConfigTest extends Specification {


    def "a"() {
        given:
        int input = 5

        when:
        int result = 5

        then:
        input == result
    }
}
