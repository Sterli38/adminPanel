package com.example.demo.dao;

import com.example.demo.entity.Player;
import com.example.demo.filter.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConditionalOnProperty(name = "repository.type", havingValue = "database")
@Repository
@RequiredArgsConstructor
public class DatabasePlayerRepository implements PlayerRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simplePlayerJdbcInsert;

    @Override
    public Player save(Player player) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", player.getName());
        parameters.put("title", player.getTitle());
        parameters.put("race", player.getRace().name());
        parameters.put("profession", player.getProfession().name());
        parameters.put("experience", player.getExperience());
        parameters.put("level", player.getLevel());
        parameters.put("untilNextLevel", player.getUntilNextLevel());
        parameters.put("birthday", new java.sql.Date(player.getBirthday().getTime()));
        parameters.put("banned", player.getBanned());

        Long id = simplePlayerJdbcInsert.executeAndReturnKey(parameters).longValue();

        player.setId(id);
        return player;
    }

    @Override
    public List<Player> getPlayerByFilter(Filter filter) {
        String sql = """
                SELECT player.id, player.name, player.title, race.name as race, profession.name as profession,
                player.experience, player.level, player.untilNextLevel, player.birthday, player.banned
                FROM player 
                JOIN race on player.race_id = race.id
                JOIN profession on player.profession_id = profession.id
                """; //TODO вынести SQL

        //TODO устранить дублирующийся код

        List<String> clauses = new ArrayList<>(); // TODO переделать весь код под эти два списка
        List<Object> values = new ArrayList<>();


        Map<String, String> map = new HashMap<>();

        if (filter.getName() != null) {
            clauses.add("player.name ILIKE ?");
            values.add("%" + filter.getName() + "%");
        }

        if (filter.getTitle() != null) {
            map.put("title ILIKE ?", "%" + filter.getTitle() + "%");
        }

        if (filter.getRace() != null) {
            map.put("race = ?", filter.getRace().name());
        }

        if (filter.getProfession() != null) {
            map.put("profession = ?", filter.getProfession().name());
        }

        if (filter.getBefore() != null) {
            map.put("before <= ?", String.valueOf(filter.getBefore()));
        }

        if (filter.getAfter() != null) {
            map.put("after >= ?", String.valueOf(String.valueOf(filter.getAfter())));
        }

        if (filter.getBanned() != null) {
            map.put("banned = ?", String.valueOf(filter.getBanned()));
        }

        if (filter.getMinExperience() != null) {
            map.put("getMinExperience >= ?", String.valueOf(filter.getMinExperience()));
        }

        if (filter.getMaxExperience() != null) {
            map.put("getMaxExperience <= ?", String.valueOf(filter.getMaxExperience()));
        }

        if (filter.getMinLevel() != null) {
            map.put("getMinLevel >= ?", String.valueOf(filter.getMinLevel()));
        }

        if (filter.getMaxLevel() != null) {
            map.put("getMaxLevel <= ?", String.valueOf(filter.getMaxLevel()));
        }

        StringBuilder stringBuilder = new StringBuilder(sql);
        if (!clauses.isEmpty()) {
            stringBuilder.append(" WHERE ");
            stringBuilder.append(String.join(" AND ", clauses));
        }

        if(filter.getOrder() != null) {
            stringBuilder.append(" ORDER BY ").append(filter.getOrder().name());
        }

        stringBuilder.append(" OFFSET ").append(getSkip(filter));
        stringBuilder.append(" LIMIT ").append(getLimit(filter));

        return jdbcTemplate.query(stringBuilder.toString(), new PlayerRowMapper(), values.toArray());
    }

    @Override
    public Player getPlayerById(Long id) {
        String getPlayerByIdSql = """
                SELECT player.id, player.name, player.title, race.name as race, profession.name as profession,
                player.experience, player.level, player.untilNextLevel, player.birthday, player.banned
                FROM player 
                JOIN race on player.race_id = race.id
                JOIN profession on player.profession_id = profession.id
                WHERE player.id = ?    
                """;
        return jdbcTemplate.queryForObject(getPlayerByIdSql, new PlayerRowMapper(), id);
    }

    @Override
    public Integer getAllPlayersCount(Filter filter) {
        String sql = """
                SELECT COUNT(*)
                FROM player 
                JOIN race on player.race_id = race.id
                JOIN profession on player.profession_id = profession.id
                """;
        StringBuilder stringBuilder = new StringBuilder(sql);
        Map<String, String> conditions = new HashMap<>();

        if (filter.getName() != null) {
            conditions.put("player.name ILIKE ?", "%" + filter.getName() + "%");
        }

        if (filter.getTitle() != null) {
            conditions.put("title ILIKE ?", "%" + filter.getTitle() + "%");
        }

        if (filter.getRace() != null) {
            conditions.put("race = ?", filter.getRace().name());
        }

        if (filter.getProfession() != null) {
            conditions.put("profession = ?", filter.getProfession().name());
        }

        if (filter.getBefore() != null) {
            conditions.put("before <= ?", String.valueOf(filter.getBefore()));
        }

        if (filter.getAfter() != null) {
            conditions.put("after >= ?", String.valueOf(String.valueOf(filter.getAfter())));
        }

        if (filter.getBanned() != null) {
            conditions.put("banned = ?", String.valueOf(filter.getBanned()));
        }

        if (filter.getMinExperience() != null) {
            conditions.put("getMinExperience >= ?", String.valueOf(filter.getMinExperience()));
        }

        if (filter.getMaxExperience() != null) {
            conditions.put("getMaxExperience <= ?", String.valueOf(filter.getMaxExperience()));
        }

        if (filter.getMinLevel() != null) {
            conditions.put("getMinLevel >= ?", String.valueOf(filter.getMinLevel()));
        }

        if (filter.getMaxLevel() != null) {
            conditions.put("getMaxLevel <= ?", String.valueOf(filter.getMaxLevel()));
        }

        if (!conditions.isEmpty()) {
            stringBuilder.append(" WHERE ");
            stringBuilder.append(String.join(" AND ", conditions.keySet()));
        }

        return jdbcTemplate.queryForObject(stringBuilder.toString(),Integer.class, conditions.values().toArray());
    }

    @Override
    public void deletePlayerById(Long id) {
        String deletePlayerByIdSql = "DELETE FROM player WHERE id = ?";
        jdbcTemplate.update(deletePlayerByIdSql, id);
    }

    private int getSkip(Filter filter) {
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

        if (filter.getPageSize() != null) {
            pageSize = filter.getPageSize();
        }

        return pageSize;
    }
}
