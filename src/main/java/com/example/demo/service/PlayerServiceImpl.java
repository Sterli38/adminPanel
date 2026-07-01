package com.example.demo.service;

import com.example.demo.controller.PlayerMapper;
import com.example.demo.dao.PlayerRepository;
import com.example.demo.entity.Player;
import com.example.demo.filter.Filter;
import com.example.demo.service.dto.PlayerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;

    @Override
    public PlayerDto create(PlayerDto playerDto) {
        Player dto = PlayerMapper.convertPlayerDtoToPlayer(playerDto);

        dto.setLevel(calculateLevel(dto.getExperience()));
        dto.setUntilNextLevel(calculateUntilNextLevel(dto.getLevel(), dto.getExperience()));

        Player createdPlayer = playerRepository.save(dto);
        return PlayerMapper.convertPlayerToPlayerDto(createdPlayer);
    }

    @Override
    public List<PlayerDto> getPlayerByFilter(Filter filter) {
        return playerRepository.getPlayerByFilter(filter).stream()
                .map(PlayerMapper::convertPlayerToPlayerDto)
                .toList();
    }

    @Override
    public PlayerDto getPlayerById(Long id) {
        return PlayerMapper.convertPlayerToPlayerDto(playerRepository.getPlayerById(id));
    }

    public Integer getAllPlayersCount(Filter filter) {
        return playerRepository.getAllPlayersCount(filter);
    }

    @Override
    public PlayerDto editPlayer(Long id, PlayerDto playerDto) { //TODO метод работает некорректно. сохраняется новый игрок при обновлении, а старый остается
        Player playerToUpdate = PlayerMapper.convertPlayerDtoToPlayer(getPlayerById(id));
        playerToUpdate.setId(id);

        if (playerDto.getName() != null) {
            playerToUpdate.setName(playerDto.getName());
        }
        if (playerDto.getTitle() != null) {
            playerToUpdate.setTitle(playerDto.getTitle());
        }
        if (playerDto.getRace() != null) {
            playerToUpdate.setRace(playerDto.getRace());
        }
        if (playerDto.getProfession() != null) {
            playerToUpdate.setProfession(playerDto.getProfession());
        }
        if (playerDto.getBirthday() != null) {
            playerToUpdate.setBirthday(playerDto.getBirthday());
        }
        if (playerDto.getBanned() != null) {
            playerToUpdate.setBanned(playerDto.getBanned());
        }
        if (playerDto.getExperience() != null) {
            playerToUpdate.setExperience(playerDto.getExperience());
        }

        playerToUpdate.setLevel(calculateLevel(playerToUpdate.getExperience()));
        playerToUpdate.setUntilNextLevel(calculateUntilNextLevel(playerToUpdate.getLevel(), playerToUpdate.getExperience()));

        Player savedPlayer = playerRepository.save(playerToUpdate);
        return PlayerMapper.convertPlayerToPlayerDto(savedPlayer);
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
