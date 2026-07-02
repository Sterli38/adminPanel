package com.example.demo.controller;

import com.example.demo.controller.request.CreatePlayerRequest;
import com.example.demo.controller.request.EditPlayerRequest;
import com.example.demo.controller.response.PlayerResponse;
import com.example.demo.filter.Filter;
import com.example.demo.service.PlayerService;
import com.example.demo.service.dto.PlayerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
public class PlayerControllerImpl implements PlayerController {
    private final PlayerService playerService;

    @Override
    public PlayerResponse create(@Valid @RequestBody CreatePlayerRequest createPlayerRequest) {
        PlayerDto playerDto = PlayerMapper.convertPlayerRequestToPlayerDto(createPlayerRequest); //TODO тут и в других местах может быть избыточен playerdto. проверить и исправить
        PlayerDto createdPlayer = playerService.create(playerDto);
        return PlayerMapper.convertPlayerDtoToPlayerResponse(createdPlayer);
    }

    @Override
    public List<PlayerResponse> getPlayerByFilter(Filter filter) {
        List<PlayerDto> receivedPlayers = playerService.getPlayerByFilter(filter);
        return receivedPlayers.stream()
                .map(PlayerMapper::convertPlayerDtoToPlayerResponse)
                .toList();
    }

    @Override
    public Integer getAllPlayerCount(Filter filter) {
        return playerService.getAllPlayersCount(filter);
    }

    @Override
    public PlayerResponse getPlayerById(@PositiveOrZero @PathVariable Long id) {
        return PlayerMapper.convertPlayerDtoToPlayerResponse(playerService.getPlayerById(id));
    }

    @Override
    public PlayerResponse editPlayer(@PositiveOrZero @PathVariable  Long id, @RequestBody EditPlayerRequest editPlayerRequest) {
        PlayerDto playerDto = PlayerMapper.convertPlayerRequestToPlayerDto(editPlayerRequest);
        PlayerDto editPLayer = playerService.editPlayer(id, playerDto);
        return PlayerMapper.convertPlayerDtoToPlayerResponse(editPLayer);
    }

    @Override
    public void deletePlayerById(@PositiveOrZero @PathVariable Long id) {
        playerService.deletePlayerById(id);
    }
}
