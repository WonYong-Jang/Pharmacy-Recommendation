package com.example.demo.direction.controller

import com.example.demo.direction.entity.Direction
import com.example.demo.direction.service.DirectionService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*

class DirectionControllerTest extends Specification {

    private MockMvc mockMvc
    private DirectionService directionService = Mock()

    def setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new DirectionController(directionService))
                .build()
    }

    def "GET /dir/{encodedId}"() {
        given:
        Direction direction = Direction.builder()
                .targetAddress("address")
                .targetLatitude(38.11)
                .targetLongitude(128.11)
                .build()

        String redirectURL = "https://map.kakao.com/link/map/address,38.11,128.11"

        when:
        directionService.findById(_) >> direction
        ResultActions result = mockMvc.perform(get("/dir/{encodedId}", "r"))

        then:
        result.andExpect(status().is3xxRedirection())  // 리다이렉트 발생 확인
                .andExpect(redirectedUrl(redirectURL)) // 리다이렉트 경로 검증
                .andDo(print())
    }
}
