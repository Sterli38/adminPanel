package com.example.demo.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FullPlayerDto extends CreatePlayerDto {
    private Integer level;
    private Integer untilNextLevel;
}
