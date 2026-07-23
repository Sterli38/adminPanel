package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Data
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String title;
    @ManyToOne
    @JoinColumn(name = "race_id")
    private RaceEntity race;
    @ManyToOne
    @JoinColumn(name = "profession_id")
    private ProfessionEntity profession;
    private Integer experience;
    private Integer level;
    @Column(name = "until_next_level")
    private Integer untilNextLevel;
    @Temporal(TemporalType.DATE)
    private Date birthday;
    private Boolean banned;

}
