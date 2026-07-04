package com.example.demo.service;

import com.example.demo.controller.PlayerMapper;
import com.example.demo.dao.PlayerRepository;
import com.example.demo.entity.Player;
import com.example.demo.filter.Filter;
import com.example.demo.service.dto.CreatePlayerDto;
import com.example.demo.service.dto.FullPlayerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;

    @Override
    public FullPlayerDto create(CreatePlayerDto createPlayerDto) {
        Player dto = PlayerMapper.convertPlayerDtoToPlayer(createPlayerDto);

        Player createdPlayer = playerRepository.save(dto);

        createdPlayer.setLevel(calculateLevel(dto.getExperience()));
        createdPlayer.setUntilNextLevel(calculateUntilNextLevel(createdPlayer.getLevel(), dto.getExperience()));

        return PlayerMapper.convertPlayerToResponsePlayerDto(createdPlayer);
    }

    @Override
    public List<FullPlayerDto> getPlayerByFilter(Filter filter) {
        return playerRepository.getPlayerByFilter(filter).stream()
                .map(PlayerMapper::convertPlayerToResponsePlayerDto)
                .toList();
    }

    @Override
    public FullPlayerDto getPlayerById(Long id) {
        return PlayerMapper.convertPlayerToResponsePlayerDto(playerRepository.getPlayerById(id));
    }

    public Integer getAllPlayersCount(Filter filter) {
        return playerRepository.getAllPlayersCount(filter);
    }

    @Override
    public FullPlayerDto editPlayer(Long id, CreatePlayerDto createPlayerDto) {
        Player playerToUpdate = PlayerMapper.convertPlayerDtoToPlayer(getPlayerById(id));
        playerToUpdate.setId(id);

        if (createPlayerDto.getName() != null) {
            playerToUpdate.setName(createPlayerDto.getName());
        }
        if (createPlayerDto.getTitle() != null) {
            playerToUpdate.setTitle(createPlayerDto.getTitle());
        }
        if (createPlayerDto.getRace() != null) {
            playerToUpdate.setRace(createPlayerDto.getRace());
        }
        if (createPlayerDto.getProfession() != null) {
            playerToUpdate.setProfession(createPlayerDto.getProfession());
        }
        if (createPlayerDto.getBirthday() != null) {
            playerToUpdate.setBirthday(createPlayerDto.getBirthday());
        }
        if (createPlayerDto.getBanned() != null) {
            playerToUpdate.setBanned(createPlayerDto.getBanned());
        }
        if (createPlayerDto.getExperience() != null) {
            playerToUpdate.setExperience(createPlayerDto.getExperience());
        }

        playerToUpdate.setLevel(calculateLevel(playerToUpdate.getExperience()));
        playerToUpdate.setUntilNextLevel(calculateUntilNextLevel(playerToUpdate.getLevel(), playerToUpdate.getExperience()));

        Player savedPlayer = playerRepository.save(playerToUpdate);
        return PlayerMapper.convertPlayerToResponsePlayerDto(savedPlayer);
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
