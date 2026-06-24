package com.example.demo.controller;

import com.example.demo.controller.request.CreatePlayerRequest;
import com.example.demo.controller.request.EditPlayerRequest;
import com.example.demo.controller.response.PlayerResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RequestMapping("/rest/players")
public interface PlayerController {
    @PostMapping
    PlayerResponse create(@Valid @RequestBody CreatePlayerRequest createPlayerRequest);

    @GetMapping("/getAllPlayers")
    List<PlayerResponse> getAllPlayers();

    List<PlayerResponse> getPlayerByFilter();

    Integer getAllPlayerCount();

    @GetMapping("/{id}")
    PlayerResponse getPlayerById(@Positive @PathVariable Long id);

    PlayerResponse editPlayer(EditPlayerRequest editPlayerRequest);

    @DeleteMapping("/{id}")
    void deletePlayerById(@Positive @PathVariable Long id);

}
