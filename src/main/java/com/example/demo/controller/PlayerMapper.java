package com.example.demo.controller;

import com.example.demo.controller.request.CreatePlayerRequest;
import com.example.demo.controller.request.EditPlayerRequest;
import com.example.demo.controller.response.PlayerResponse;
import com.example.demo.entity.Player;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.filter.Filter;
import com.example.demo.filter.PlayerOrder;
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

    public static PlayerDto convertPlayerRequestToPlayerDto(EditPlayerRequest playerRequest) {
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

    public static Player convertPlayerDtoToPlayer(PlayerDto playerDto) {
        Player player = new Player();
        player.setName(playerDto.getName());
        player.setTitle(playerDto.getTitle());
        player.setRace(playerDto.getRace());
        player.setProfession(playerDto.getProfession());
        player.setBirthday(playerDto.getBirthday());
        player.setBanned(playerDto.getBanned());
        player.setExperience(playerDto.getExperience());
        player.setLevel(playerDto.getLevel());
        player.setUntilNextLevel(playerDto.getUntilNextLevel());

        return player;
    }

    public static PlayerDto convertPlayerToPlayerDto(Player player) {
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

    public static Filter convertRequestParamsToFilter(String name,
                                                      String title,
                                                      Race race,
                                                      Profession profession,
                                                      Long after,
                                                      Long before,
                                                      Boolean banned,
                                                      Integer minExperience,
                                                      Integer maxExperience,
                                                      Integer minLevel,
                                                      Integer maxLevel,
                                                      PlayerOrder order,
                                                      Integer pageNumber,
                                                      Integer pageSize) {
        Filter filter = new Filter();
        filter.setName(name);
        filter.setTitle(title);
        filter.setRace(race);
        filter.setProfession(profession);
        filter.setAfter(after);
        filter.setBefore(before);
        filter.setBanned(banned);
        filter.setMinExperience(minExperience);
        filter.setMaxExperience(maxExperience);
        filter.setMinLevel(minLevel);
        filter.setMaxLevel(maxLevel);
        filter.setOrder(order);
        filter.setPageNumber(pageNumber);
        filter.setPageSize(pageSize);

        return filter;
    }
}
