package com.example.demo.controller.request;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import lombok.Data;

import javax.validation.constraints.*;


@Data
public class CreatePlayerRequest {
    @NotBlank
    @Size(max = 12)
    private String name;
    @NotBlank
    @Size(max = 30)
    private String title;
    @NotNull
    private Race race;
    @NotNull
    private Profession profession;
    @Positive
    private Long birthday;
    @NotNull
    private Boolean banned;
    @NotNull
    @Positive
    private Integer experience;
}
