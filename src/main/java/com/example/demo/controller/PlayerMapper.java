package com.example.demo.controller;

import com.example.demo.controller.request.CreatePlayerRequest;
import com.example.demo.controller.response.PlayerResponse;
import com.example.demo.service.dto.PlayerDto;

import java.util.Date;

public class PlayerMapper {
    public static PlayerDto convertPlayerRequestToPlayerDto(CreatePlayerRequest playerRequest) {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setName(playerRequest.getName());
        playerDto.setTitle(playerRequest.getTitle());
        playerDto.setRace(playerRequest.getRace());
        playerDto.setProfession(playerRequest.getProfession());
        playerDto.setBirthday(new Date(playerRequest.getBirthday()));
        playerDto.setBanned(playerRequest.getBanned());
        playerDto.setExperience(playerRequest.getExperience());

        return playerDto;
    }

    public static PlayerResponse convertPlayerDtoToPlayerResponse(PlayerDto playerDto) {
        PlayerResponse playerResponse = new PlayerResponse();
        playerResponse.setId(playerDto.getId());
        playerResponse.setName(playerDto.getName());
        playerResponse.setTitle(playerDto.getTitle());
        playerResponse.setRace(playerDto.getRace());
        playerResponse.setProfession(playerDto.getProfession());
        playerResponse.setBirthday(playerDto.getBirthday().getTime());
        playerResponse.setBanned(playerDto.getBanned());
        playerResponse.setExperience(playerDto.getExperience());
        playerResponse.setLevel(playerDto.getLevel());
        playerResponse.setUntilNextLevel(playerDto.getUntilNextLevel());

        return playerResponse;
    }
}
