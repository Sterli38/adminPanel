package com.example.demo.dao;

import com.example.demo.entity.Player;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.filter.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

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
        Integer raceId = getRaceId(player.getRace());
        Integer professionId = getProfessionId(player.getProfession());

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", player.getName());
        parameters.put("title", player.getTitle());
        parameters.put("race_id", raceId);
        parameters.put("profession_id", professionId);
        parameters.put("experience", player.getExperience());
        parameters.put("level", player.getLevel());
        parameters.put("until_next_level", player.getUntilNextLevel());
        parameters.put("birthday", player.getBirthday());
        parameters.put("banned", player.getBanned());

        if (player.getId() == null) {
            Long id = simplePlayerJdbcInsert.executeAndReturnKey(parameters).longValue();
            player.setId(id);

            return player;
        } else {
            updatePlayer(player);

            return player;
        }
    }

    private void  updatePlayer(Player player) {
        Object[] args = {
                player.getName(),
                player.getTitle(),
                player.getRace().name(),
                player.getProfession().name(),
                player.getExperience(),
                player.getLevel(),
                player.getUntilNextLevel(),
                new java.sql.Date(player.getBirthday().getTime()),
                player.getBanned(),
                player.getId()
        };
        jdbcTemplate.update(PlayerQueries.updatePlayer, args);

    }

    @Override
    public List<Player> getPlayerByFilter(Filter filter) {
        StringBuilder sql = new StringBuilder(PlayerQueries.getPlayerByFilter);
        SqlBuilder builder = buildSql(sql, filter, true);

        return jdbcTemplate.query(sql.toString(), new PlayerRowMapper(), builder.getValues().toArray());
    }

    @Override
    public Player getPlayerById(Long id) {
        try {
            return jdbcTemplate.queryForObject(PlayerQueries.getPlayerById, new PlayerRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    @Override
    public Integer getAllPlayersCount(Filter filter) {
        StringBuilder sql = new StringBuilder(PlayerQueries.getAllPlayersCount);
        SqlBuilder builder = buildSql(sql, filter, false);

        return jdbcTemplate.queryForObject(sql.toString(), Integer.class, builder.getValues().toArray());
    }

    @Override
    public void deletePlayerById(Long id) {
        jdbcTemplate.update(PlayerQueries.deletePlayerById, id);
    }

    private Integer getRaceId(Race raceName) {
        String sql = "SELECT id FROM race WHERE name = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, raceName.name());
    }

    private Integer getProfessionId(Profession professionName) {
        String sql = "SELECT id FROM profession WHERE name = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, professionName.name());
    }

    private SqlBuilder buildSql(StringBuilder sql, Filter filter, boolean withPaging) {
        SqlBuilder builder = new SqlBuilder();
        sql.append(builder.buildCondition(filter));

        if (!withPaging) {
            return builder;
        }

        if (filter.getOrder() != null) {
            sql.append(" ORDER BY ").append(filter.getOrder().name());
        }
        sql.append(" LIMIT ").append(getLimit(filter));
        sql.append(" OFFSET ").append(getSkip(filter));

        return builder;
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
