package com.example.demo.dao;

import com.example.demo.entity.Player;
import com.example.demo.filter.Filter;

import java.util.List;

public interface PlayerRepository {
    Player create(Player player);

    List<Player> getAllPlayers();

    List<Player> getPlayerByFilter(Filter filter);

    Integer getAllPlayerCount();

    Player getPlayerById(Long id);

    Player editPlayer(Player player);

    Player deletePlayerById(Long id);
}
