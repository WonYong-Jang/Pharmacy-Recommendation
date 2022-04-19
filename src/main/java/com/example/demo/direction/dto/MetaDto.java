package com.example.demo.direction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class MetaDto {

    @JsonProperty("total_count")
    private Integer totalCount;


}
