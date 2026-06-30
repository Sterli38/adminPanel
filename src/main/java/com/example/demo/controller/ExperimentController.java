package com.example.demo.controller;

import com.example.demo.controller.request.CreatePlayerRequest;
import com.example.demo.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/experiment")
@RestController
@RequiredArgsConstructor
public class ExperimentController {
    private final PlayerService playerService;

    @PostMapping("/createNewPlayer")
    public void create(@RequestBody List<CreatePlayerRequest> newPlayerList) {
        newPlayerList.stream()
                .map(player -> playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(player)))
                .toList();
    }
}
