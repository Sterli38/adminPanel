package com.example.demo.service;

import com.example.demo.service.dto.PlayerDto;

import java.util.List;

public interface PlayerService {
    PlayerDto create(PlayerDto playerDto);

    List<PlayerDto> getAllPlayers();

    List<PlayerDto> getPlayerByFilter();

    Integer getAllPlayerCount();

    PlayerDto getPlayerById(Long id);

    PlayerDto editPlayer(PlayerDto player);

    void deletePlayerById(Long id);
}
