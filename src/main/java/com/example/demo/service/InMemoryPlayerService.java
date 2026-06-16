package com.example.demo.service;

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
        Player player = playerRepository.create(convertPlayerDtoToPlayer(playerDto));
        player.setLevel(calculateLevel(player.getExperience()));
        player.setUntilNextLevel(calculateUntilNextLevel(player.getLevel(), player.getExperience()));
        return convertPlayerToPlayerDto(player);
    }

    @Override
    public List<PlayerDto> getAllPlayers() {
       return playerRepository.getAllPlayers().stream()
               .map(this::convertPlayerToPlayerDto)
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
        return convertPlayerToPlayerDto(playerRepository.getPlayerById(id));
    }

    @Override
    public PlayerDto editPlayer(PlayerDto player) {
        return null;
    }

    @Override
    public PlayerDto deletePlayerById(Long id) {
        return null;
    }

    private Integer calculateLevel(Integer experience) {
        return (int) ((Math.sqrt(2500 + 200 * experience) - 50) / 100);
    }

    private Integer calculateUntilNextLevel(Integer level, Integer experience) {
        return 50 * (level + 1) * (level + 2) - experience;
    }

    private Player convertPlayerDtoToPlayer(PlayerDto playerDto) {
        Player player = new Player();
        player.setName(playerDto.getName());
        player.setTitle(playerDto.getTitle());
        player.setRace(playerDto.getRace());
        player.setProfession(playerDto.getProfession());
        player.setBirthday(playerDto.getBirthday());
        player.setBanned(playerDto.getBanned());
        player.setExperience(playerDto.getExperience());
        return player;
    }

    private PlayerDto convertPlayerToPlayerDto(Player player) {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setId(player.getId());
        playerDto.setName(player.getName());
        playerDto.setTitle(player.getTitle());
        playerDto.setRace(player.getRace());
        playerDto.setProfession(player.getProfession());
        playerDto.setBirthday(player.getBirthday());
        playerDto.setBanned(player.getBanned());
        playerDto.setExperience(player.getExperience());
        playerDto.setLevel(player.getLevel());
        playerDto.setUntilNextLevel(player.getUntilNextLevel());
        return playerDto;
    }
}
