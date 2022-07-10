package com.example.demo.direction.controller

import com.example.demo.direction.dto.InputDto
import com.example.demo.direction.dto.OutputDto
import com.example.demo.pharmacy.service.PharmacyRecommendationService
import org.assertj.core.util.Lists
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class FormControllerTest extends Specification {

    private MockMvc mockMvc
    private PharmacyRecommendationService pharmacyRecommendationService = Mock()
    private List<OutputDto> outputDtoList

    def setup() {
        // FormController를 MockMvc 객체로 만든다.
        mockMvc = MockMvcBuilders.standaloneSetup(new FormController(pharmacyRecommendationService))
                .build()

        outputDtoList = Lists.newArrayList(
                OutputDto.builder()
                        .pharmacyName("pharmacy1")
                        .build(),
                OutputDto.builder()
                        .pharmacyName("pharmacy2")
                        .build()
        )
    }

    def "GET /"() {

        expect:
        // FormController 의 "/" URI를 get방식으로 호출
        mockMvc.perform(get("/"))
                .andExpect(status().isOk()) // 예상 값을 검증한다.
                .andExpect(view().name("main"))
                .andDo(log())
    }

    def "POST /search"() {
        given:
        String address = "서울 성북구 종암동"
        InputDto inputDto = new InputDto()
        inputDto.setAddress(address)

        when:
        ResultActions result = mockMvc.perform(
                post("/search")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED) // @ModelAttribute 매핑 검증을 위한 content type 지정
                        .content("address=서울 성북구 종암동"))

        then:
        1 * pharmacyRecommendationService.recommendPharmacyList({argument ->
            assert argument == address // mock 객체의 argument 검증
        }) >> outputDtoList

        result.andExpect(status().isOk())
                .andExpect(view().name("output"))
                .andExpect(model().attributeExists("outputFormList"))
                .andExpect(model().attribute("outputFormList", outputDtoList))
                .andDo(print())
    }
}
