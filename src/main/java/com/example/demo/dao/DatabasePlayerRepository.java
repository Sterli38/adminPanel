package com.example.demo.dao;

import com.example.demo.entity.Player;
import com.example.demo.filter.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@ConditionalOnProperty(name = "repository.type", havingValue = "database")
@Repository
@RequiredArgsConstructor
public class DatabasePlayerRepository implements PlayerRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Player save(Player player) {
        String savePlayerSql = "INSERT INTO players(" +
                "name, title, race_id, profession_id, experience, level, untilNextLevel, birthday, banned)" +
                " values(" +
                "?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String getRaceId = "SELECT id FROM race WHERE name = ?";
        String getProfessionId = "SELECT id FROM profession WHERE name = ?";

        Integer raceId = jdbcTemplate.queryForObject(getRaceId, Integer.class, player.getRace().name());
        Integer professionId = jdbcTemplate.queryForObject(getProfessionId, Integer.class, player.getProfession().name());

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(savePlayerSql, new String[]{"id"});
            ps.setString(1, player.getName());
            ps.setString(2, player.getTitle());
            ps.setInt(3, raceId);
            ps.setInt(4, professionId);
            ps.setInt(5, player.getExperience());
            ps.setInt(6, player.getLevel());
            ps.setInt(7, player.getUntilNextLevel());
            ps.setDate(8, new java.sql.Date(player.getBirthday().getTime()));
            ps.setBoolean(9, player.getBanned());

            return ps;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        player.setId(id);

        return player;
    }

    @Override
    public List<Player> getPlayerByFilter(Filter filter) {
        return List.of();
    }

    @Override
    public Player getPlayerById(Long id) {
        String getPlayerByIdSql = """
                SELECT players.id, players.name, players.title, race.name as race, profession.name as profession,
                players.experience, players.level, players.untilNextLevel, players.birthday, players.banned
                FROM players 
                JOIN race on players.race_id = race.id
                JOIN profession on players.profession_id = profession.id
                WHERE players.id = ?    
                """;
        Player player = jdbcTemplate.queryForObject(getPlayerByIdSql, new PlayerRowMapper(), id);
        return player;
    }

    @Override
    public Integer getAllPlayersCount(Filter filter) {
        return 0;
    }

    @Override
    public void deletePlayerById(Long id) {
        String deletePlayerByIdSql = "DELETE FROM players WHERE id = ?";
        jdbcTemplate.update(deletePlayerByIdSql, id);
    }
}
