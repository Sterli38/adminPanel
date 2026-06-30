package com.example.demo.dao;

import com.example.demo.entity.Player;
import com.example.demo.filter.Filter;

import java.util.List;

public interface PlayerRepository {
    Player create(Player player);

    List<Player> getAllPlayers();

    List<Player> getPlayerByFilter(Filter filter);

    Player getPlayerById(Long id);

    Player editPlayer(Long id, Player player);

    void deletePlayerById(Long id);
}
