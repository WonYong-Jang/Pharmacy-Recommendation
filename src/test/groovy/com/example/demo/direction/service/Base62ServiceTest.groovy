package com.example.demo.direction.service


import spock.lang.Specification
import spock.lang.Subject

class Base62ServiceTest extends Specification {

    @Subject
    private Base62Service base62Service

    def setup() {
        base62Service = new Base62Service()
    }

    def "check base 62 encoder/decoder"() {
        given:
        long num = 5

        when:
        String encodedId = base62Service.encodeDirectionId(num)

        long directionId = base62Service.decodeDirectionId(encodedId)

        then:
        num == directionId
    }
}
