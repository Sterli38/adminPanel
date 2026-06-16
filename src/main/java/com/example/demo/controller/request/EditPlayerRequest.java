package com.example.demo.controller.request;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import lombok.Data;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class EditPlayerRequest {
    @Size(max = 12)
    private String name;
    @Size(max = 30)
    private String title;
    private Race race;
    private Profession profession;
    @Positive
    private Long birthday;
    private Boolean banned;
    private Integer experience;
}
