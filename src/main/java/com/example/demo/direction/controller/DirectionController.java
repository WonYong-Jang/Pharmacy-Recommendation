package com.example.demo.direction.controller;

import com.example.demo.direction.entity.Direction;
import com.example.demo.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@Slf4j
@RequiredArgsConstructor
public class DirectionController {

    private final DirectionService directionService;

    @GetMapping("/dir/{encodedId}")
    public String searchDirection(@PathVariable("encodedId") String encodedId) {

        Direction resultDirection = directionService.findById(encodedId);

        String params = String.join(",", resultDirection.getTargetAddress(),
                String.valueOf(resultDirection.getTargetLatitude()), String.valueOf(resultDirection.getTargetLongitude()));
        String result = UriComponentsBuilder.fromHttpUrl("https://map.kakao.com/link/map/" + params)
                .toUriString();

        return "redirect:"+result;
    }

}
