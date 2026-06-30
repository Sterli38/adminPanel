package com.example.demo.controller;

import com.example.demo.controller.request.CreatePlayerRequest;
import com.example.demo.controller.request.EditPlayerRequest;
import com.example.demo.controller.response.PlayerResponse;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.filter.Filter;
import com.example.demo.filter.PlayerOrder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RequestMapping("/rest/players")
public interface PlayerController {
    @PostMapping
    PlayerResponse create(@Valid @RequestBody CreatePlayerRequest createPlayerRequest);

    @GetMapping("/getAllPlayers")
    List<PlayerResponse> getAllPlayers();

    @GetMapping
    List<PlayerResponse> getPlayerByFilter(Filter filter);

    @GetMapping("/count")
    Integer getAllPlayerCount(@RequestParam(required = false) String name,
                              @RequestParam(required = false) String title,
                              @RequestParam(required = false) Race race,
                              @RequestParam(required = false) Profession profession,
                              @RequestParam(required = false) Long after,
                              @RequestParam(required = false) Long before,
                              @RequestParam(required = false) Boolean banned,
                              @RequestParam(required = false) Integer minExperience,
                              @RequestParam(required = false) Integer maxExperience,
                              @RequestParam(required = false) Integer minLevel,
                              @RequestParam(required = false) Integer maxLevel);

    @GetMapping("/{id}")
    PlayerResponse getPlayerById(@Positive @PathVariable Long id);

    @PostMapping("/{id}")
    PlayerResponse editPlayer(@Positive @PathVariable Long id, @RequestBody EditPlayerRequest editPlayerRequest);

    @DeleteMapping("/{id}")
    void deletePlayerById(@Positive @PathVariable Long id);

}
