package com.example.demo.service;

import com.example.demo.controller.PlayerMapper;
import com.example.demo.dao.JpaPlayerRepository;
import com.example.demo.dao.ProfessionRepository;
import com.example.demo.dao.RaceRepository;
import com.example.demo.entity.Player;
import com.example.demo.filter.Filter;
import com.example.demo.service.dto.CreatePlayerDto;
import com.example.demo.service.dto.FullPlayerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final JpaPlayerRepository playerRepository;
    private final RaceRepository raceRepository;
    private final ProfessionRepository professionRepository;

    @Override
    public FullPlayerDto create(CreatePlayerDto createPlayerDto) {
        Player dto = new Player();
        dto.setName(createPlayerDto.getName());
        dto.setTitle(createPlayerDto.getTitle());
        dto.setRace(raceRepository.getByName(createPlayerDto.getRace().name()));
        dto.setProfession(professionRepository.getByName(createPlayerDto.getProfession().name()));
        dto.setBirthday(createPlayerDto.getBirthday());
        dto.setBanned(createPlayerDto.getBanned());
        dto.setExperience(createPlayerDto.getExperience());

        dto.setLevel(calculateLevel(dto.getExperience()));
        dto.setUntilNextLevel(calculateUntilNextLevel(dto.getLevel(), dto.getExperience()));

        Player createdPlayer = playerRepository.save(dto);

        return PlayerMapper.convertPlayerToResponsePlayerDto(createdPlayer);
    }

    @Override
    public List<FullPlayerDto> getPlayerByFilter(Filter filter) {
        return playerRepository.getPlayers(filter).stream()
                .map(PlayerMapper::convertPlayerToResponsePlayerDto)
                .toList();
    }
    @Override
    public FullPlayerDto getPlayerById(Long id) {
        Player player = playerRepository.findById(id).orElse(null);
        return player == null ? null : PlayerMapper.convertPlayerToResponsePlayerDto(player);
    }

    public Integer getAllPlayersCount(Filter filter) {
        return playerRepository.getAllPlayersCount(filter);
    }

    @Override
    public FullPlayerDto editPlayer(Long id, CreatePlayerDto createPlayerDto) {
        Player playerToUpdate = playerRepository.findById(id).orElseThrow();
        playerToUpdate.setId(id);

        if (createPlayerDto.getName() != null) {
            playerToUpdate.setName(createPlayerDto.getName());
        }
        if (createPlayerDto.getTitle() != null) {
            playerToUpdate.setTitle(createPlayerDto.getTitle());
        }
        if (createPlayerDto.getRace() != null) {
            playerToUpdate.setRace(raceRepository.getByName(createPlayerDto.getRace().name()));
        }
        if (createPlayerDto.getProfession() != null) {
            playerToUpdate.setProfession(professionRepository.getByName(createPlayerDto.getProfession().name()));
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
    @Transactional
    public void deletePlayerById(Long id) {
        playerRepository.deleteById(id);
    }

    private Integer calculateLevel(Integer experience) {
        return (int) ((Math.sqrt(2500 + 200 * experience) - 50) / 100);
    }

    private Integer calculateUntilNextLevel(Integer level, Integer experience) {
        return 50 * (level + 1) * (level + 2) - experience;
    }
}
