package com.example.demo.controller;

import com.example.demo.controller.request.CreatePlayerRequest;
import com.example.demo.controller.request.EditPlayerRequest;
import com.example.demo.controller.response.PlayerResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/players")
public interface PlayerController {
    @PostMapping("/createPlayer")
    PlayerResponse create(CreatePlayerRequest createPlayerRequest);

    @GetMapping("/getAllPlayers")
    List<PlayerResponse> getAllPlayers();

    List<PlayerResponse> getPlayerByFilter();

    Integer getAllPlayerCount();

    @GetMapping("/getPlayerById/{id}")
    PlayerResponse getPlayerById(Long id);

    PlayerResponse editPlayer(EditPlayerRequest editPlayerRequest);

    void deletePlayerById(Long id);

}
