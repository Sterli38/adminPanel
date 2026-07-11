package com.example.demo.controller;

import com.example.demo.controller.request.CreatePlayerRequest;
import com.example.demo.controller.request.EditPlayerRequest;
import com.example.demo.controller.response.PlayerResponse;
import com.example.demo.filter.Filter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RequestMapping("/rest/players")
public interface PlayerController {
    @PostMapping
    PlayerResponse create(@Valid @RequestBody CreatePlayerRequest createPlayerRequest);
    @GetMapping
    List<PlayerResponse> getPlayerByFilter(Filter filter);

    @GetMapping("/count")
    Integer getAllPlayerCount(Filter filter);

    @GetMapping("/{id}")
    PlayerResponse getPlayerById(@PositiveOrZero @PathVariable Long id);

    @PostMapping("/{id}")
    PlayerResponse editPlayer(@PositiveOrZero @PathVariable Long id, @RequestBody EditPlayerRequest editPlayerRequest);

    @DeleteMapping("/{id}")
    void deletePlayerById(@PositiveOrZero @PathVariable Long id);

}
