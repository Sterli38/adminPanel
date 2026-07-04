package com.example.demo.service;

import com.example.demo.filter.Filter;
import com.example.demo.service.dto.CreatePlayerDto;
import com.example.demo.service.dto.FullPlayerDto;

import java.util.List;

public interface PlayerService {
    FullPlayerDto create(CreatePlayerDto createPlayerDto);

    List<FullPlayerDto> getPlayerByFilter(Filter filter);

    FullPlayerDto getPlayerById(Long id);

    Integer getAllPlayersCount(Filter filter);

    FullPlayerDto editPlayer(Long id, CreatePlayerDto createPlayerDto);

    void deletePlayerById(Long id);
}
