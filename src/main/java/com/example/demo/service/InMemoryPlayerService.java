package com.example.demo.service;

import com.example.demo.controller.PlayerMapper;
import com.example.demo.dao.PlayerRepository;
import com.example.demo.entity.Player;
import com.example.demo.service.dto.PlayerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InMemoryPlayerService implements PlayerService {
    private final PlayerRepository playerRepository;

    @Override
    public PlayerDto create(PlayerDto playerDto) {
        playerDto.setLevel(calculateLevel(playerDto.getExperience()));
        playerDto.setUntilNextLevel(calculateUntilNextLevel(playerDto.getLevel(), playerDto.getExperience()));
        Player createdPlayer = playerRepository.create(PlayerMapper.convertPlayerDtoToPlayer(playerDto));
        return PlayerMapper.convertPlayerToPlayerDto(createdPlayer);
    }

    @Override
    public List<PlayerDto> getAllPlayers() {
        return playerRepository.getAllPlayers().stream()
                .map(PlayerMapper::convertPlayerToPlayerDto)
                .toList();
    }

    @Override
    public List<PlayerDto> getPlayerByFilter() {
        return List.of();
    }

    @Override
    public Integer getAllPlayerCount() {
        return 0;
    }

    @Override
    public PlayerDto getPlayerById(Long id) {
        return PlayerMapper.convertPlayerToPlayerDto(playerRepository.getPlayerById(id));
    }

    @Override
    public PlayerDto editPlayer(PlayerDto player) {
        return null;
    }

    @Override
    public void deletePlayerById(Long id) {
        playerRepository.deletePlayerById(id);
    }

    private Integer calculateLevel(Integer experience) {
        return (int) ((Math.sqrt(2500 + 200 * experience) - 50) / 100);
    }

    private Integer calculateUntilNextLevel(Integer level, Integer experience) {
        return 50 * (level + 1) * (level + 2) - experience;
    }
}
