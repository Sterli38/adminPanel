package com.example.demo.dao;

import com.example.demo.entity.Player;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.filter.Filter;
import com.example.demo.filter.FilterPredicateBuilder;
import com.example.demo.filter.PlayerOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryPlayerRepository implements PlayerRepository {
    private final boolean isTestDataEnabled;

    private Long id = 0L;
    private final Map<Long, Player> playerStorage = new ConcurrentHashMap<>();

    public InMemoryPlayerRepository(@Value("${test.data.enabled}") boolean isTestDataEnabled) {
        this.isTestDataEnabled = isTestDataEnabled;
    }

    @PostConstruct
    private void initData() {
        if (!isTestDataEnabled) {
            return;
        }

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
    public Player save(Player player) {
        Player newPlayer = new Player();
        if(player.getId() == null) {
            newPlayer.setId(++id);
        } else {
            newPlayer.setId(player.getId());
        }
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
    public List<Player> getPlayerByFilter(Filter filter) {
        return playerStorage.values().stream()
                .filter(FilterPredicateBuilder.buildPredicate(filter))
                .sorted(new PlayerComparator(filter))
                .skip(getSkip(filter))
                .limit(getLimit(filter))
                .toList();
    }

    private int getSkip(Filter filter) {
        if(filter == null) {
            return 0;
        }

        int pageNumber = 0;
        if (filter.getPageNumber() != null) {
            pageNumber = filter.getPageNumber();
        }

        int pageSize = 3;
        if (filter.getPageSize() != null) {
            pageSize = filter.getPageSize();
        }

        return pageNumber * pageSize;
    }

    private int getLimit(Filter filter) {
        int pageSize = 3;

        if(filter == null) {
            return pageSize;
        }

        if(filter.getPageSize() != null) {
            pageSize = filter.getPageSize();
        }

        return pageSize;
    }

    @Override
    public Player getPlayerById(Long id) {
        return playerStorage.get(id);
    }

    public Integer getAllPlayersCount(Filter filter) {
        return (int) playerStorage.values().stream()
                .filter(FilterPredicateBuilder.buildPredicate(filter))
                .count();
    }

    @Override
    public void deletePlayerById(Long id) {
        playerStorage.remove(id);
    }

    public void clear() {
        playerStorage.clear();
        id = 0L;
    }
}
