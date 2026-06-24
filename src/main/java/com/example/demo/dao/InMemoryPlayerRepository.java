package com.example.demo.dao;

import com.example.demo.entity.Player;
import com.example.demo.filter.Filter;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryPlayerRepository implements PlayerRepository {
    private Long id = 0L;
    private final Map<Long, Player> playerStorage = new HashMap<>();

    @Override
    public Player create(Player player) {
        Player newPlayer = new Player();
        newPlayer.setId(++id);
        newPlayer.setName(player.getName());
        newPlayer.setTitle(player.getTitle());
        newPlayer.setRace(player.getRace());
        newPlayer.setProfession(player.getProfession());
        newPlayer.setExperience(player.getExperience());
        newPlayer.setLevel(player.getLevel());
        newPlayer.setUntilNextLevel(player.getUntilNextLevel());
        newPlayer.setBirthday(player.getBirthday());
        newPlayer.setBanned(player.getBanned());
        playerStorage.put(newPlayer.getId(), newPlayer);

        return newPlayer;
    }

    @Override
    public List<Player> getAllPlayers() {
        return List.copyOf(playerStorage.values());
    }

    @Override
    public List<Player> getPlayerByFilter(Filter filter) {
        return null;
    }

    @Override
    public Integer getAllPlayerCount() {
        return playerStorage.size();
    }

    @Override
    public Player getPlayerById(Long id) {
        return playerStorage.get(id);
    }

    @Override
    public Player editPlayer(Player player) {
        return null;
    }

    @Override
    public void deletePlayerById(Long id) {
        playerStorage.remove(id);
    }
}
