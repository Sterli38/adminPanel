package com.example.demo.controller;

import com.example.demo.controller.request.CreatePlayerRequest;
import com.example.demo.controller.request.EditPlayerRequest;
import com.example.demo.controller.response.PlayerResponse;
import com.example.demo.service.PlayerService;
import com.example.demo.service.dto.PlayerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlayerControllerImpl implements PlayerController {
    private final PlayerService playerService;

    @Override
    public PlayerResponse create(@Valid @RequestBody CreatePlayerRequest createPlayerRequest) {
        PlayerDto playerDto = PlayerMapper.convertPlayerRequestToPlayerDto(createPlayerRequest);
        PlayerDto createdPlayer = playerService.create(playerDto);
        return PlayerMapper.convertPlayerDtoToPlayerResponse(createdPlayer);
    }

    @Override
    public List<PlayerResponse> getAllPlayers() {
        return playerService.getAllPlayers().stream()
                .map(PlayerMapper::convertPlayerDtoToPlayerResponse)
                .toList();
    }

    @Override
    public List<PlayerResponse> getPlayerByFilter() {
        return List.of();
    }

    @Override
    public Integer getAllPlayerCount() {
        return 0;
    }

    @Override
    public PlayerResponse getPlayerById(@PathVariable Long id) {
        return PlayerMapper.convertPlayerDtoToPlayerResponse(playerService.getPlayerById(id));
    }

    @Override
    public PlayerResponse editPlayer(EditPlayerRequest editPlayerRequest) {
        return null;
    }

    @Override
    public void deletePlayerById(Long id) {

    }

}
