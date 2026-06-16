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
    private Integer MinExperience;
    private Integer MaxExperience;
    private Integer MinLevel;
    private Integer MaxLevel;
    private PlayerOrder order;
    private Integer pageNumber;
    private Integer pageSize;

}
