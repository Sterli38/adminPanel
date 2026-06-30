package com.example.demo.dao;

import com.example.demo.entity.Player;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.filter.Filter;
import com.example.demo.filter.FilterPredicateBuilder;
import com.example.demo.filter.PlayerOrder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryPlayerRepository implements PlayerRepository {
    private Long id = 0L;
    private final Map<Long, Player> playerStorage = new ConcurrentHashMap<>();

    @PostConstruct
    private void initData() {
        Random random = new Random();
        for (int i = 1; i < 50; i++) {
            Player player = new Player();
            player.setId(id++);
            player.setRace(Race.values()[random.nextInt((Race.values().length))]);
            player.setBanned(random.nextInt(2) == 1);
            player.setName("name" + i + player.getRace());
            player.setLevel(i * random.nextInt(12));
            player.setTitle("title" + random.nextInt(123213213));
            player.setBirthday(new Date(random.nextLong(1782550531)));
            player.setProfession(Profession.values()[random.nextInt((Profession.values().length))]);
            player.setExperience(random.nextInt());
            player.setUntilNextLevel(random.nextInt(123123));

            playerStorage.put(player.getId(), player);
        }
    }

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
        return playerStorage.values().stream()
                .filter(FilterPredicateBuilder.buildPredicate(filter))
                .sorted((a, b) -> { //TODO вынести в отдельный метод
                    if (filter.getOrder() == PlayerOrder.ID) {
                        return a.getId().compareTo(b.getId());
                    } else if (filter.getOrder() == PlayerOrder.NAME) {
                        return a.getName().compareTo(b.getName());
                    } else if (filter.getOrder() == PlayerOrder.EXPERIENCE) {
                        return a.getExperience().compareTo(b.getExperience());
                    } else if (filter.getOrder() == PlayerOrder.BIRTHDAY) {
                        return a.getBirthday().compareTo(b.getBirthday());
                    } else if (filter.getOrder() == PlayerOrder.LEVEL) {
                        return a.getLevel().compareTo(b.getLevel());
                    }
                    return 0;
                })
                .skip(filter.getPageNumber() == null || filter.getPageSize() == null ? 0 : (long) filter.getPageNumber() * filter.getPageSize())
                .limit(filter.getPageSize() == null ? 1000000000 : filter.getPageSize())
                .toList();
    }

    @Override
    public Player getPlayerById(Long id) {
        return playerStorage.get(id);
    }

    @Override
    public Player editPlayer(Long id, Player player) {
        Player editPlayer = getPlayerById(id);
        if (player.getName() != null) {
            editPlayer.setName(player.getName());
        }
        if (player.getTitle() != null) {
            editPlayer.setTitle(player.getTitle());
        }
        if (player.getRace() != null) {
            editPlayer.setRace(player.getRace());
        }
        if (player.getProfession() != null) {
            editPlayer.setProfession(player.getProfession());
        }
        if (player.getBirthday() != null) {
            editPlayer.setBirthday(player.getBirthday());
        }
        if (player.getBanned() != null) {
            editPlayer.setBanned(player.getBanned());
        }
        if (player.getExperience() != null) {
            editPlayer.setExperience(player.getExperience());
        }

        editPlayer.setLevel(player.getLevel());
        editPlayer.setUntilNextLevel(player.getUntilNextLevel());

        return editPlayer;
    }

    @Override
    public void deletePlayerById(Long id) {
        playerStorage.remove(id);
    }
}
