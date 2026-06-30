package com.example.demo.service;

import com.example.demo.filter.Filter;
import com.example.demo.service.dto.PlayerDto;

import java.util.List;

public interface PlayerService {
    PlayerDto create(PlayerDto playerDto);

    List<PlayerDto> getAllPlayers();

    List<PlayerDto> getPlayerByFilter(Filter filter);

    PlayerDto getPlayerById(Long id);

    PlayerDto editPlayer(Long id, PlayerDto playerDto);

    void deletePlayerById(Long id);
}
