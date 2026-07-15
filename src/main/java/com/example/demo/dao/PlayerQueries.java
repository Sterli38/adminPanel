package com.example.demo.dao;

public interface PlayerQueries {
    String updatePlayer = """
            UPDATE player SET name = ?, title = ?, race_id = (SELECT id FROM race WHERE name = ?), 
            profession_id = (SELECT id FROM profession WHERE name = ?), experience = ?, level = ?,
             until_next_level = ?, birthday = ?, banned = ? WHERE id = ?""";

    String getPlayerById = """
            SELECT player.id, player.name, player.title, race.name as race, profession.name as profession,
            player.experience, player.level, player.until_next_level, player.birthday, player.banned
            FROM player 
            JOIN race on player.race_id = race.id
            JOIN profession on player.profession_id = profession.id
            WHERE player.id = ?    
            """;

    String getPlayerByFilter = """
             SELECT player.id, player.name, player.title, race.name as race, profession.name as profession,
            player.experience, player.level, player.until_next_level, player.birthday, player.banned
             FROM player
             JOIN race on player.race_id = race.id
             JOIN profession on player.profession_id = profession.id
            """;

    String getAllPlayersCount = """
            SELECT COUNT(*)
            FROM player
            JOIN race on player.race_id = race.id
            JOIN profession on player.profession_id = profession.id
            """;

    String deletePlayerById = "DELETE FROM player WHERE id = ?";
}
