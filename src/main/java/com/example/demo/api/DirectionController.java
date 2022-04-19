package com.example.demo.api;

import com.example.demo.direction.entity.Direction;
import com.example.demo.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequiredArgsConstructor
public class DirectionController {

    private final DirectionService directionService;

    @GetMapping("/dir/{encodedId}")
    public String searchDirection(@PathVariable("encodedId") String encodedId) {

        Direction resultDirection = directionService.findById(encodedId);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://map.naver.com/index.nhn");
        builder.queryParam("slat", resultDirection.getInputLatitude())
                .queryParam("slng", resultDirection.getInputLongitude())
                .queryParam("stext", resultDirection.getInputAddress())
                .queryParam("elat", resultDirection.getTargetLatitude())
                .queryParam("elng", resultDirection.getTargetLongitude())
                .queryParam("etext", resultDirection.getTargetAddress())
                .queryParam("menu","route")
                .queryParam("pathType","3");
        return String.format("redirect:%s", builder.toUriString());
    }

}
