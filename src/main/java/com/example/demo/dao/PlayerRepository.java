package com.example.demo.dao;

import com.example.demo.entity.Player;
import com.example.demo.filter.Filter;

import java.util.List;

public interface PlayerRepository {
    Player save(Player player);

    List<Player> getAllPlayers();

    List<Player> getPlayerByFilter(Filter filter);

    Player getPlayerById(Long id);

    Integer getAllPlayersCount(Filter filter);

    void deletePlayerById(Long id);
}
