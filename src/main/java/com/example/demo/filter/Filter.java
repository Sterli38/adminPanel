package com.example.demo.filter;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import lombok.Data;

@Data
public class Filter {
    private String name;
    private String title;
    private Race race;
    private Profession profession;
    private Long after;
    private Long before;
    private Boolean banned;
    private Integer minExperience;
    private Integer maxExperience;
    private Integer minLevel;
    private Integer maxLevel;
    private PlayerOrder order;
    private Integer pageNumber = 0;
    private Integer pageSize = 3;
}
