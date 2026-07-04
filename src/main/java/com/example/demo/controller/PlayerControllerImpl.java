package com.example.demo.controller;

import com.example.demo.controller.request.CreatePlayerRequest;
import com.example.demo.controller.request.EditPlayerRequest;
import com.example.demo.controller.response.PlayerCountResponse;
import com.example.demo.controller.response.PlayerResponse;
import com.example.demo.filter.Filter;
import com.example.demo.service.PlayerService;
import com.example.demo.service.dto.CreatePlayerDto;
import com.example.demo.service.dto.FullPlayerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
        CreatePlayerDto createPlayerDto = PlayerMapper.convertPlayerRequestToPlayerDto(createPlayerRequest);
        FullPlayerDto createdPlayer = playerService.create(createPlayerDto);
        return PlayerMapper.convertResponsePlayerDtoToPlayerResponse(createdPlayer);
    }

    @Override
    public List<PlayerResponse> getPlayerByFilter(@RequestParam Filter filter) {
        List<FullPlayerDto> receivedPlayers = playerService.getPlayerByFilter(filter);
        return receivedPlayers.stream()
                .map(PlayerMapper::convertResponsePlayerDtoToPlayerResponse)
                .toList();
    }

    @Override
    public PlayerCountResponse getAllPlayerCount(@RequestParam Filter filter) {
        return PlayerMapper.convertPlayerCountToPlayerCountResponse(playerService.getAllPlayersCount(filter));
    }

    @Override
    public PlayerResponse getPlayerById(@PositiveOrZero @PathVariable Long id) {
        return PlayerMapper.convertResponsePlayerDtoToPlayerResponse(playerService.getPlayerById(id));
    }

    @Override
    public PlayerResponse editPlayer(@PositiveOrZero @PathVariable  Long id, @RequestBody EditPlayerRequest editPlayerRequest) {
        CreatePlayerDto createPlayerDto = PlayerMapper.convertPlayerRequestToPlayerDto(editPlayerRequest);
        FullPlayerDto editPLayer = playerService.editPlayer(id, createPlayerDto);
        return PlayerMapper.convertResponsePlayerDtoToPlayerResponse(editPLayer);
    }

    @Override
    public void deletePlayerById(@PositiveOrZero @PathVariable Long id) {
        playerService.deletePlayerById(id);
    }
}
