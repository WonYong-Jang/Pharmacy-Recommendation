package com.example.demo.api;

import com.example.demo.direction.dto.InputDto;
import com.example.demo.direction.dto.KakaoApiResponseDto;
import com.example.demo.direction.service.AddressConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class DirectionController {

    private final AddressConverterService addressConverterService;

    @GetMapping("/")
    public String getDirection(Model model) {
        model.addAttribute("form", new InputDto());
        return "input";
    }

    @PostMapping("/")
    public String postDirection(@ModelAttribute("input") InputDto inputDto) {

        KakaoApiResponseDto kakaoApiResponseDto = addressConverterService.convertAddressToGeospatialData(inputDto.getAddress());

        System.out.println(kakaoApiResponseDto.toString());
        return "output";
    }

}
