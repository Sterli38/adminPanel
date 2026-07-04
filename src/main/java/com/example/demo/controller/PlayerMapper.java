package com.example.demo.controller;

import com.example.demo.controller.request.CreatePlayerRequest;
import com.example.demo.controller.request.EditPlayerRequest;
import com.example.demo.controller.response.PlayerCountResponse;
import com.example.demo.controller.response.PlayerResponse;
import com.example.demo.entity.Player;
import com.example.demo.service.dto.CreatePlayerDto;
import com.example.demo.service.dto.FullPlayerDto;

import java.util.Date;

public class PlayerMapper {
    public static CreatePlayerDto convertPlayerRequestToPlayerDto(CreatePlayerRequest playerRequest) {
        CreatePlayerDto createPlayerDto = new CreatePlayerDto();
        createPlayerDto.setName(playerRequest.getName());
        createPlayerDto.setTitle(playerRequest.getTitle());
        createPlayerDto.setRace(playerRequest.getRace());
        createPlayerDto.setProfession(playerRequest.getProfession());
        createPlayerDto.setBirthday(new Date(playerRequest.getBirthday()));
        createPlayerDto.setBanned(playerRequest.getBanned());
        createPlayerDto.setExperience(playerRequest.getExperience());

        return createPlayerDto;
    }

    public static CreatePlayerDto convertPlayerRequestToPlayerDto(EditPlayerRequest playerRequest) {
        CreatePlayerDto createPlayerDto = new CreatePlayerDto();
        createPlayerDto.setName(playerRequest.getName());
        createPlayerDto.setTitle(playerRequest.getTitle());
        createPlayerDto.setRace(playerRequest.getRace());
        createPlayerDto.setProfession(playerRequest.getProfession());
        createPlayerDto.setBirthday(new Date(playerRequest.getBirthday()));
        createPlayerDto.setBanned(playerRequest.getBanned());
        createPlayerDto.setExperience(playerRequest.getExperience());

        return createPlayerDto;
    }

    public static PlayerResponse convertResponsePlayerDtoToPlayerResponse(FullPlayerDto responsePlayerDto) {
        PlayerResponse playerResponse = new PlayerResponse();
        playerResponse.setId(responsePlayerDto.getId());
        playerResponse.setName(responsePlayerDto.getName());
        playerResponse.setTitle(responsePlayerDto.getTitle());
        playerResponse.setRace(responsePlayerDto.getRace());
        playerResponse.setProfession(responsePlayerDto.getProfession());
        playerResponse.setBirthday(responsePlayerDto.getBirthday().getTime());
        playerResponse.setBanned(responsePlayerDto.getBanned());
        playerResponse.setExperience(responsePlayerDto.getExperience());
        playerResponse.setLevel(responsePlayerDto.getLevel());
        playerResponse.setUntilNextLevel(responsePlayerDto.getUntilNextLevel());

        return playerResponse;
    }

    public static Player convertPlayerDtoToPlayer(CreatePlayerDto createPlayerDto) {
        Player player = new Player();
        player.setName(createPlayerDto.getName());
        player.setTitle(createPlayerDto.getTitle());
        player.setRace(createPlayerDto.getRace());
        player.setProfession(createPlayerDto.getProfession());
        player.setBirthday(createPlayerDto.getBirthday());
        player.setBanned(createPlayerDto.getBanned());
        player.setExperience(createPlayerDto.getExperience());

        return player;
    }

    public static FullPlayerDto convertPlayerToResponsePlayerDto(Player player) {
        FullPlayerDto responsePlayerDto = new FullPlayerDto();
        responsePlayerDto.setId(player.getId());
        responsePlayerDto.setName(player.getName());
        responsePlayerDto.setTitle(player.getTitle());
        responsePlayerDto.setRace(player.getRace());
        responsePlayerDto.setProfession(player.getProfession());
        responsePlayerDto.setBirthday(player.getBirthday());
        responsePlayerDto.setBanned(player.getBanned());
        responsePlayerDto.setExperience(player.getExperience());
        responsePlayerDto.setLevel(player.getLevel());
        responsePlayerDto.setUntilNextLevel(player.getUntilNextLevel());

        return responsePlayerDto;
    }

    public static PlayerCountResponse convertPlayerCountToPlayerCountResponse(Integer count) {
        PlayerCountResponse playerCountResponse = new PlayerCountResponse();
        playerCountResponse.setCount(count);
        return playerCountResponse;
    }
}
