package com.example.demo.config

import com.example.demo.DemoApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Specification

@WebAppConfiguration
@SpringBootTest
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
