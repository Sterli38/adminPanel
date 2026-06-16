package com.example.demo.service.dto;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import lombok.Data;

import java.util.Date;

@Data
public class PlayerDto {
    private Long id;
    private String name;
    private String title;
    private Race race;
    private Profession profession;
    private Integer experience;
    private Integer level;
    private Integer untilNextLevel;
    private Date birthday;
    private Boolean banned;

}
