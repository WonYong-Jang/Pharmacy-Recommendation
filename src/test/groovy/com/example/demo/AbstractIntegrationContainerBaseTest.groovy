package com.example.demo

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.MariaDBContainer
import spock.lang.Specification

@ActiveProfiles("test") // application-test.yml 를 사용
@SpringBootTest
abstract class AbstractIntegrationContainerBaseTest extends Specification {

    //static final MariaDBContainer MY_DATABASE_CONTAINER
    static final GenericContainer MY_REDIS_CONTAINER

    static {
//        MY_DATABASE_CONTAINER = new MariaDBContainer("mariadb:10")
//                .withDatabaseName("pharmacyRecommendation") // Database 지정하기

        MY_REDIS_CONTAINER = new GenericContainer<>("redis:6")
                .withExposedPorts(6379)

        //MY_DATABASE_CONTAINER.start()
        MY_REDIS_CONTAINER.start()

        System.setProperty("spring.redis.host", MY_REDIS_CONTAINER.getHost())
        System.setProperty("spring.redis.port", MY_REDIS_CONTAINER.getMappedPort(6379) + "")
    }
}
