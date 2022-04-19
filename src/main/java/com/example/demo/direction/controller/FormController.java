package com.example.demo.direction.controller;

import com.example.demo.direction.dto.InputDto;
import com.example.demo.direction.dto.OutputDto;
import com.example.demo.pharmacy.service.PharmacyRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class FormController {
    private final PharmacyRecommendationService pharmacyRecommendationService;

    @GetMapping("/")
    public String getDirection(Model model) {
        model.addAttribute("inputForm", new InputDto());
        return "input";
    }

    @PostMapping("/")
    public String postDirection(@ModelAttribute("input") InputDto inputDto, Model model) {

        List<OutputDto> outputList =
                pharmacyRecommendationService.recommendPharmacyList(inputDto.getAddress());

        model.addAttribute("outputFormList", outputList);
        return "output";
    }
}
