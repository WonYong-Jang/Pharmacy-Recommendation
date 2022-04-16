package com.example.demo.direction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class DirectionController {

    @GetMapping("/")
    public String getDirection(Model model) {
        model.addAttribute("form", new InputDto());
        return "input";
    }

    @PostMapping("/")
    public String postDirection(@ModelAttribute("input") InputDto inputDto) {

        return "output";
    }

}
