package com.example.demo;

import com.example.demo.controller.PlayerMapper;
import com.example.demo.controller.request.CreatePlayerRequest;
import com.example.demo.controller.request.EditPlayerRequest;
import com.example.demo.dao.InMemoryPlayerRepository;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.filter.Filter;
import com.example.demo.filter.PlayerOrder;
import com.example.demo.service.PlayerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RpgTest {
    @Autowired
    private PlayerService playerService;
    @Autowired
    private InMemoryPlayerRepository playerRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void clear() {
        playerRepository.clear();
    }

    @Test
    public void createPlayerTest() throws Exception {
        CreatePlayerRequest createPlayerRequest = new CreatePlayerRequest();
        createPlayerRequest.setName("player");
        createPlayerRequest.setTitle("title");
        createPlayerRequest.setRace(Race.HUMAN);
        createPlayerRequest.setProfession(Profession.PALADIN);
        createPlayerRequest.setBirthday(1640995200000L);
        createPlayerRequest.setBanned(false);
        createPlayerRequest.setExperience(1000);

        mockMvc.perform(
                        post("/rest/players")
                                .content(objectMapper.writeValueAsString(createPlayerRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("player"))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.race").value(Race.HUMAN.name()))
                .andExpect(jsonPath("$.profession").value(Profession.PALADIN.name()))
                .andExpect(jsonPath("$.birthday").value(1640995200000L))
                .andExpect(jsonPath("$.banned").value(false))
                .andExpect(jsonPath("$.experience").value(1000))
                .andExpect(jsonPath("$.level").value(4))
                .andExpect(jsonPath("$.untilNextLevel").value(500));
    }

    @Test
    public void createInvalidPlayerTest() {
        //TODO реализовать
    }

    @Test
    public void getPlayerByFilterByNameTest() throws Exception {
        CreatePlayerRequest expectedTestPlayer1 = new CreatePlayerRequest();
        expectedTestPlayer1.setName("testPlayer");
        expectedTestPlayer1.setTitle("testTitle");
        expectedTestPlayer1.setRace(Race.HUMAN);
        expectedTestPlayer1.setProfession(Profession.PALADIN);
        expectedTestPlayer1.setExperience(1000);
        expectedTestPlayer1.setBirthday(1673481600000L);
        expectedTestPlayer1.setBanned(false);

        CreatePlayerRequest expectedTestPlayer2 = new CreatePlayerRequest();
        expectedTestPlayer2.setName("testPlayer2");
        expectedTestPlayer2.setTitle("testTitle");
        expectedTestPlayer2.setRace(Race.HUMAN);
        expectedTestPlayer2.setProfession(Profession.PALADIN);
        expectedTestPlayer2.setExperience(1000);
        expectedTestPlayer2.setBirthday(1673481600000L);
        expectedTestPlayer2.setBanned(false);

        CreatePlayerRequest nonMatchingPlayer = new CreatePlayerRequest();
        nonMatchingPlayer.setName("testTest");
        nonMatchingPlayer.setTitle("testTitle");
        nonMatchingPlayer.setRace(Race.HUMAN);
        nonMatchingPlayer.setProfession(Profession.PALADIN);
        nonMatchingPlayer.setExperience(1000);
        nonMatchingPlayer.setBirthday(1673481600000L);
        nonMatchingPlayer.setBanned(false);

        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(expectedTestPlayer1));
        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(expectedTestPlayer2));
        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(nonMatchingPlayer));

        Filter filter = new Filter();
        filter.setName("ayer");

        mockMvc.perform(
                        get("/rest/players")
                                .param("name", filter.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("testPlayer"))
                .andExpect(jsonPath("$[0].title").value("testTitle"))
                .andExpect(jsonPath("$[0].race").value(Race.HUMAN.name()))
                .andExpect(jsonPath("$[0].profession").value(Profession.PALADIN.name()))
                .andExpect(jsonPath("$[0].birthday").value(1673481600000L))
                .andExpect(jsonPath("$[0].banned").value(false))
                .andExpect(jsonPath("$[0].experience").value(1000))
                .andExpect(jsonPath("$[0].level").value(4))
                .andExpect(jsonPath("$[0].untilNextLevel").value(500)) //TODO сделать проверку на второго игрока
                .andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    public void getPlayerByFilterByTitleTest() throws Exception {
        CreatePlayerRequest expectedTestPlayer1 = new CreatePlayerRequest();
        expectedTestPlayer1.setName("testPlayer");
        expectedTestPlayer1.setTitle("testTitleTitle");
        expectedTestPlayer1.setRace(Race.HUMAN);
        expectedTestPlayer1.setProfession(Profession.PALADIN);
        expectedTestPlayer1.setExperience(1000);
        expectedTestPlayer1.setBirthday(1673481600000L);
        expectedTestPlayer1.setBanned(false);

        CreatePlayerRequest nonMatchingPlayer = new CreatePlayerRequest();
        nonMatchingPlayer.setName("testPlayer");
        nonMatchingPlayer.setTitle("test");
        nonMatchingPlayer.setRace(Race.HUMAN);
        nonMatchingPlayer.setProfession(Profession.PALADIN);
        nonMatchingPlayer.setExperience(1000);
        nonMatchingPlayer.setBirthday(1673481600000L);
        nonMatchingPlayer.setBanned(false);

        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(expectedTestPlayer1));
        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(nonMatchingPlayer));

        Filter filter = new Filter();
        filter.setTitle("Title");

        mockMvc.perform(
                        get("/rest/players")
                                .param("Title", filter.getTitle()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("testPlayer"))
                .andExpect(jsonPath("$[0].title").value("testTitleTitle"))
                .andExpect(jsonPath("$[0].race").value(Race.HUMAN.name()))
                .andExpect(jsonPath("$[0].profession").value(Profession.PALADIN.name()))
                .andExpect(jsonPath("$[0].birthday").value(1673481600000L))
                .andExpect(jsonPath("$[0].banned").value(false))
                .andExpect(jsonPath("$[0].experience").value(1000))
                .andExpect(jsonPath("$[0].level").value(4))
                .andExpect(jsonPath("$[0].untilNextLevel").value(500));

    }

    @Test
    public void getPlayerByFilterByRaceTest() throws Exception {
        CreatePlayerRequest expectedTestPlayer1 = new CreatePlayerRequest();
        expectedTestPlayer1.setName("testPlayer");
        expectedTestPlayer1.setTitle("testTitle");
        expectedTestPlayer1.setRace(Race.HUMAN);
        expectedTestPlayer1.setProfession(Profession.PALADIN);
        expectedTestPlayer1.setExperience(1000);
        expectedTestPlayer1.setBirthday(1673481600000L);
        expectedTestPlayer1.setBanned(false);

        CreatePlayerRequest nonMatchingPlayer = new CreatePlayerRequest();
        nonMatchingPlayer.setName("testPlayer");
        nonMatchingPlayer.setTitle("testTitle");
        nonMatchingPlayer.setRace(Race.HOBBIT);
        nonMatchingPlayer.setProfession(Profession.PALADIN);
        nonMatchingPlayer.setExperience(1000);
        nonMatchingPlayer.setBirthday(1673481600000L);
        nonMatchingPlayer.setBanned(false);

        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(expectedTestPlayer1));
        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(nonMatchingPlayer));

        Filter filter = new Filter();
        filter.setRace(Race.HUMAN);

        mockMvc.perform(
                        get("/rest/players")
                                .param("Race", filter.getRace().name()))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("testPlayer"))
                .andExpect(jsonPath("$[0].title").value("testTitle"))
                .andExpect(jsonPath("$[0].race").value(Race.HUMAN.name()))
                .andExpect(jsonPath("$[0].profession").value(Profession.PALADIN.name()))
                .andExpect(jsonPath("$[0].birthday").value(1673481600000L))
                .andExpect(jsonPath("$[0].banned").value(false))
                .andExpect(jsonPath("$[0].experience").value(1000))
                .andExpect(jsonPath("$[0].level").value(4))
                .andExpect(jsonPath("$[0].untilNextLevel").value(500))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void getPlayerByFilterByProfessionTest() throws Exception {
        CreatePlayerRequest expectedTestPlayer1 = new CreatePlayerRequest();
        expectedTestPlayer1.setName("testPlayer");
        expectedTestPlayer1.setTitle("testTitle");
        expectedTestPlayer1.setRace(Race.HUMAN);
        expectedTestPlayer1.setProfession(Profession.PALADIN);
        expectedTestPlayer1.setExperience(1000);
        expectedTestPlayer1.setBirthday(1673481600000L);
        expectedTestPlayer1.setBanned(false);

        CreatePlayerRequest expectedTestPlayer2 = new CreatePlayerRequest();
        expectedTestPlayer2.setName("testPlayer");
        expectedTestPlayer2.setTitle("testTitle");
        expectedTestPlayer2.setRace(Race.HUMAN);
        expectedTestPlayer2.setProfession(Profession.PALADIN);
        expectedTestPlayer2.setExperience(1000);
        expectedTestPlayer2.setBirthday(1673481600000L);
        expectedTestPlayer2.setBanned(false);

        CreatePlayerRequest nonMatchingPlayer = new CreatePlayerRequest();
        nonMatchingPlayer.setName("testPlayer");
        nonMatchingPlayer.setTitle("testTitle");
        nonMatchingPlayer.setRace(Race.HUMAN);
        nonMatchingPlayer.setProfession(Profession.CLERIC);
        nonMatchingPlayer.setExperience(1000);
        nonMatchingPlayer.setBirthday(1673481600000L);
        nonMatchingPlayer.setBanned(false);

        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(expectedTestPlayer1));
        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(expectedTestPlayer2));
        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(nonMatchingPlayer));

        Filter filter = new Filter();
        filter.setProfession(Profession.PALADIN);

        mockMvc.perform(
                        get("/rest/players")
                                .param("Profession", filter.getProfession().name()))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("testPlayer"))
                .andExpect(jsonPath("$[0].title").value("testTitle"))
                .andExpect(jsonPath("$[0].race").value(Race.HUMAN.name()))
                .andExpect(jsonPath("$[0].profession").value(Profession.PALADIN.name()))
                .andExpect(jsonPath("$[0].birthday").value(1673481600000L))
                .andExpect(jsonPath("$[0].banned").value(false))
                .andExpect(jsonPath("$[0].experience").value(1000))
                .andExpect(jsonPath("$[0].level").value(4))
                .andExpect(jsonPath("$[0].untilNextLevel").value(500))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getPlayerByFilterByBirthdayTest() throws Exception {
        CreatePlayerRequest expectedTestPlayer1 = new CreatePlayerRequest();
        expectedTestPlayer1.setName("testPlayer");
        expectedTestPlayer1.setTitle("testTitle");
        expectedTestPlayer1.setRace(Race.HUMAN);
        expectedTestPlayer1.setProfession(Profession.PALADIN);
        expectedTestPlayer1.setExperience(1000);
        expectedTestPlayer1.setBirthday(1673481600000L);
        expectedTestPlayer1.setBanned(false);

        CreatePlayerRequest expectedTestPlayer2 = new CreatePlayerRequest();
        expectedTestPlayer2.setName("testPlayer");
        expectedTestPlayer2.setTitle("testTitle");
        expectedTestPlayer2.setRace(Race.HUMAN);
        expectedTestPlayer2.setProfession(Profession.PALADIN);
        expectedTestPlayer2.setExperience(1000);
        expectedTestPlayer2.setBirthday(1673481600001L);
        expectedTestPlayer2.setBanned(false);

        CreatePlayerRequest expectedTestPlayer3 = new CreatePlayerRequest();
        expectedTestPlayer3.setName("testPlayer");
        expectedTestPlayer3.setTitle("testTitle");
        expectedTestPlayer3.setRace(Race.HUMAN);
        expectedTestPlayer3.setProfession(Profession.PALADIN);
        expectedTestPlayer3.setExperience(1000);
        expectedTestPlayer3.setBirthday(1673481600002L);
        expectedTestPlayer3.setBanned(false);

        CreatePlayerRequest nonMatchingPlayer = new CreatePlayerRequest();
        nonMatchingPlayer.setName("testPlayer");
        nonMatchingPlayer.setTitle("testTitle");
        nonMatchingPlayer.setRace(Race.HUMAN);
        nonMatchingPlayer.setProfession(Profession.PALADIN);
        nonMatchingPlayer.setExperience(1000);
        nonMatchingPlayer.setBirthday(16734816000000L);
        nonMatchingPlayer.setBanned(false);

        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(expectedTestPlayer1));
        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(expectedTestPlayer2));
        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(expectedTestPlayer3));
        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(nonMatchingPlayer));

        Filter filter = new Filter();
        filter.setAfter(1673481600000L);
        filter.setBefore(1673481600002L);
        filter.setOrder(PlayerOrder.BIRTHDAY);

        mockMvc.perform(
                        get("/rest/players")
                                .param("after", filter.getAfter().toString())
                                .param("before", filter.getBefore().toString()))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("testPlayer"))
                .andExpect(jsonPath("$[0].title").value("testTitle"))
                .andExpect(jsonPath("$[0].race").value(Race.HUMAN.name()))
                .andExpect(jsonPath("$[0].profession").value(Profession.PALADIN.name()))
                .andExpect(jsonPath("$[0].birthday").value(1673481600000L))
                .andExpect(jsonPath("$[0].banned").value(false))
                .andExpect(jsonPath("$[0].experience").value(1000))
                .andExpect(jsonPath("$[0].level").value(4))
                .andExpect(jsonPath("$[0].untilNextLevel").value(500))
                .andExpect(jsonPath("$[1].id").isNumber())
                .andExpect(jsonPath("$[1].name").value("testPlayer"))
                .andExpect(jsonPath("$[1].title").value("testTitle"))
                .andExpect(jsonPath("$[1].race").value(Race.HUMAN.name()))
                .andExpect(jsonPath("$[1].profession").value(Profession.PALADIN.name()))
                .andExpect(jsonPath("$[1].birthday").value(1673481600001L))
                .andExpect(jsonPath("$[1].banned").value(false))
                .andExpect(jsonPath("$[1].experience").value(1000))
                .andExpect(jsonPath("$[1].level").value(4))
                .andExpect(jsonPath("$[1].untilNextLevel").value(500))
                .andExpect(jsonPath("$[2].id").isNumber())
                .andExpect(jsonPath("$[2].name").value("testPlayer"))
                .andExpect(jsonPath("$[2].title").value("testTitle"))
                .andExpect(jsonPath("$[2].race").value(Race.HUMAN.name()))
                .andExpect(jsonPath("$[2].profession").value(Profession.PALADIN.name()))
                .andExpect(jsonPath("$[2].birthday").value(1673481600002L))
                .andExpect(jsonPath("$[2].banned").value(false))
                .andExpect(jsonPath("$[2].experience").value(1000))
                .andExpect(jsonPath("$[2].level").value(4))
                .andExpect(jsonPath("$[2].untilNextLevel").value(500))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void getPlayerByFilterByExperienceTest() throws Exception {
        CreatePlayerRequest expectedTestPlayer1 = new CreatePlayerRequest();
        expectedTestPlayer1.setName("testPlayer");
        expectedTestPlayer1.setTitle("testTitle");
        expectedTestPlayer1.setRace(Race.HUMAN);
        expectedTestPlayer1.setProfession(Profession.PALADIN);
        expectedTestPlayer1.setExperience(1040);
        expectedTestPlayer1.setBirthday(1673481600000L);
        expectedTestPlayer1.setBanned(false);

        CreatePlayerRequest expectedTestPlayer2 = new CreatePlayerRequest();
        expectedTestPlayer2.setName("testPlayer");
        expectedTestPlayer2.setTitle("testTitle");
        expectedTestPlayer2.setRace(Race.HUMAN);
        expectedTestPlayer2.setProfession(Profession.PALADIN);
        expectedTestPlayer2.setExperience(934);
        expectedTestPlayer2.setBirthday(1673481600000L);
        expectedTestPlayer2.setBanned(false);

        CreatePlayerRequest nonMatchingPlayer = new CreatePlayerRequest();
        nonMatchingPlayer.setName("testPlayer");
        nonMatchingPlayer.setTitle("testTitle");
        nonMatchingPlayer.setRace(Race.HUMAN);
        nonMatchingPlayer.setProfession(Profession.PALADIN);
        nonMatchingPlayer.setExperience(20000);
        nonMatchingPlayer.setBirthday(1673481600000L);
        nonMatchingPlayer.setBanned(false);

        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(expectedTestPlayer1));
        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(expectedTestPlayer2));
        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(nonMatchingPlayer));

        Filter filter = new Filter();
        filter.setMinExperience(900);
        filter.setMaxExperience(1100);

        mockMvc.perform(
                        get("/rest/players")
                                .param("MinExperience", filter.getMinExperience().toString())
                                .param("MaxExperience", filter.getMaxExperience().toString()))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("testPlayer"))
                .andExpect(jsonPath("$[0].title").value("testTitle"))
                .andExpect(jsonPath("$[0].race").value(Race.HUMAN.name()))
                .andExpect(jsonPath("$[0].profession").value(Profession.PALADIN.name()))
                .andExpect(jsonPath("$[0].birthday").value(1673481600000L))
                .andExpect(jsonPath("$[0].banned").value(false))
                .andExpect(jsonPath("$[0].experience").value(1040))
                .andExpect(jsonPath("$[0].level").value(4))
                .andExpect(jsonPath("$[0].untilNextLevel").value(460))
                .andExpect(jsonPath("$[1].id").isNumber())
                .andExpect(jsonPath("$[1].name").value("testPlayer"))
                .andExpect(jsonPath("$[1].title").value("testTitle"))
                .andExpect(jsonPath("$[1].race").value(Race.HUMAN.name()))
                .andExpect(jsonPath("$[1].profession").value(Profession.PALADIN.name()))
                .andExpect(jsonPath("$[1].birthday").value(1673481600000L))
                .andExpect(jsonPath("$[1].banned").value(false))
                .andExpect(jsonPath("$[1].experience").value(934))
                .andExpect(jsonPath("$[1].level").value(3))
                .andExpect(jsonPath("$[1].untilNextLevel").value(66))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getPlayerByFilterByLevelTest() throws Exception {
        CreatePlayerRequest expectedTestPlayer1 = new CreatePlayerRequest();
        expectedTestPlayer1.setName("testPlayer");
        expectedTestPlayer1.setTitle("testTitle");
        expectedTestPlayer1.setRace(Race.HUMAN);
        expectedTestPlayer1.setProfession(Profession.PALADIN);
        expectedTestPlayer1.setExperience(1000);
        expectedTestPlayer1.setBirthday(1673481600000L);
        expectedTestPlayer1.setBanned(false);

        CreatePlayerRequest expectedTestPlayer2 = new CreatePlayerRequest();
        expectedTestPlayer2.setName("testPlayer");
        expectedTestPlayer2.setTitle("testTitle");
        expectedTestPlayer2.setRace(Race.HUMAN);
        expectedTestPlayer2.setProfession(Profession.PALADIN);
        expectedTestPlayer2.setExperience(2000);
        expectedTestPlayer2.setBirthday(1673481600000L);
        expectedTestPlayer2.setBanned(false);

        CreatePlayerRequest nonMatchingPlayer = new CreatePlayerRequest();
        nonMatchingPlayer.setName("testPlayer");
        nonMatchingPlayer.setTitle("testTitle");
        nonMatchingPlayer.setRace(Race.HUMAN);
        nonMatchingPlayer.setProfession(Profession.PALADIN);
        nonMatchingPlayer.setExperience(9000);
        nonMatchingPlayer.setBirthday(1673481600000L);
        nonMatchingPlayer.setBanned(false);

        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(expectedTestPlayer1));
        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(expectedTestPlayer2));
        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(nonMatchingPlayer));

        Filter filter = new Filter();
        filter.setMinLevel(4);
        filter.setMaxLevel(5);

        mockMvc.perform(
                        get("/rest/players")
                                .param("MinLevel", filter.getMinLevel().toString())
                                .param("MaxLevel", filter.getMaxLevel().toString()))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("testPlayer"))
                .andExpect(jsonPath("$[0].title").value("testTitle"))
                .andExpect(jsonPath("$[0].race").value(Race.HUMAN.name()))
                .andExpect(jsonPath("$[0].profession").value(Profession.PALADIN.name()))
                .andExpect(jsonPath("$[0].birthday").value(1673481600000L))
                .andExpect(jsonPath("$[0].banned").value(false))
                .andExpect(jsonPath("$[0].experience").value(1000))
                .andExpect(jsonPath("$[0].level").value(4))
                .andExpect(jsonPath("$[0].untilNextLevel").value(500))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("testPlayer"))
                .andExpect(jsonPath("$[0].title").value("testTitle"))
                .andExpect(jsonPath("$[0].race").value(Race.HUMAN.name()))
                .andExpect(jsonPath("$[0].profession").value(Profession.PALADIN.name()))
                .andExpect(jsonPath("$[0].birthday").value(1673481600000L))
                .andExpect(jsonPath("$[0].banned").value(false))
                .andExpect(jsonPath("$[0].experience").value(1000))
                .andExpect(jsonPath("$[0].level").value(4))
                .andExpect(jsonPath("$[0].untilNextLevel").value(500))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getPlayerByFilterByOrderNameTest() throws Exception {
        CreatePlayerRequest testPlayer1 = new CreatePlayerRequest();
        testPlayer1.setName("a");
        testPlayer1.setTitle("testTitle");
        testPlayer1.setRace(Race.HUMAN);
        testPlayer1.setProfession(Profession.PALADIN);
        testPlayer1.setExperience(1000);
        testPlayer1.setBirthday(1673481600000L);
        testPlayer1.setBanned(false);

        CreatePlayerRequest testPlayer2 = new CreatePlayerRequest();
        testPlayer2.setName("c");
        testPlayer2.setTitle("testTitle");
        testPlayer2.setRace(Race.HUMAN);
        testPlayer2.setProfession(Profession.PALADIN);
        testPlayer2.setExperience(1000);
        testPlayer2.setBirthday(1673481600000L);
        testPlayer2.setBanned(false);

        CreatePlayerRequest testPlayer3 = new CreatePlayerRequest();
        testPlayer3.setName("b");
        testPlayer3.setTitle("testTitle");
        testPlayer3.setRace(Race.HUMAN);
        testPlayer3.setProfession(Profession.PALADIN);
        testPlayer3.setExperience(1000);
        testPlayer3.setBirthday(1673481600000L);
        testPlayer3.setBanned(false);

        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(testPlayer1));
        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(testPlayer2));
        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(testPlayer3));

        Filter filter = new Filter();
        filter.setOrder(PlayerOrder.NAME);

        mockMvc.perform(
                        get("/rest/players")
                                .param("order", filter.getOrder().name()))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("a"))
                .andExpect(jsonPath("$[0].title").value("testTitle"))
                .andExpect(jsonPath("$[0].race").value(Race.HUMAN.name()))
                .andExpect(jsonPath("$[0].profession").value(Profession.PALADIN.name()))
                .andExpect(jsonPath("$[0].birthday").value(1673481600000L))
                .andExpect(jsonPath("$[0].banned").value(false))
                .andExpect(jsonPath("$[0].experience").value(1000))
                .andExpect(jsonPath("$[0].level").value(4))
                .andExpect(jsonPath("$[0].untilNextLevel").value(500))
                .andExpect(jsonPath("$[1].id").isNumber())
                .andExpect(jsonPath("$[1].name").value("b"))
                .andExpect(jsonPath("$[1].title").value("testTitle"))
                .andExpect(jsonPath("$[1].race").value(Race.HUMAN.name()))
                .andExpect(jsonPath("$[1].profession").value(Profession.PALADIN.name()))
                .andExpect(jsonPath("$[1].birthday").value(1673481600000L))
                .andExpect(jsonPath("$[1].banned").value(false))
                .andExpect(jsonPath("$[1].experience").value(1000))
                .andExpect(jsonPath("$[1].level").value(4))
                .andExpect(jsonPath("$[1].untilNextLevel").value(500))
                .andExpect(jsonPath("$[2].id").isNumber())
                .andExpect(jsonPath("$[2].name").value("c"))
                .andExpect(jsonPath("$[2].title").value("testTitle"))
                .andExpect(jsonPath("$[2].race").value(Race.HUMAN.name()))
                .andExpect(jsonPath("$[2].profession").value(Profession.PALADIN.name()))
                .andExpect(jsonPath("$[2].birthday").value(1673481600000L))
                .andExpect(jsonPath("$[2].banned").value(false))
                .andExpect(jsonPath("$[2].experience").value(1000))
                .andExpect(jsonPath("$[2].level").value(4))
                .andExpect(jsonPath("$[2].untilNextLevel").value(500));
    }

    @Test
    public void getPlayerByFilterByOrderExperienceTest() throws Exception {
        CreatePlayerRequest testPlayer1 = new CreatePlayerRequest();
        testPlayer1.setName("a");
        testPlayer1.setTitle("testTitle");
        testPlayer1.setRace(Race.HUMAN);
        testPlayer1.setProfession(Profession.PALADIN);
        testPlayer1.setExperience(1000);
        testPlayer1.setBirthday(1673481600000L);
        testPlayer1.setBanned(false);

        CreatePlayerRequest testPlayer2 = new CreatePlayerRequest();
        testPlayer2.setName("b");
        testPlayer2.setTitle("testTitle");
        testPlayer2.setRace(Race.HUMAN);
        testPlayer2.setProfession(Profession.PALADIN);
        testPlayer2.setExperience(1001);
        testPlayer2.setBirthday(1673481600000L);
        testPlayer2.setBanned(false);

        CreatePlayerRequest testPlayer3 = new CreatePlayerRequest();
        testPlayer3.setName("c");
        testPlayer3.setTitle("testTitle");
        testPlayer3.setRace(Race.HUMAN);
        testPlayer3.setProfession(Profession.PALADIN);
        testPlayer3.setExperience(999);
        testPlayer3.setBirthday(1673481600000L);
        testPlayer3.setBanned(false);

        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(testPlayer1));
        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(testPlayer2));
        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(testPlayer3));

        Filter filter = new Filter();
        filter.setOrder(PlayerOrder.EXPERIENCE);

        mockMvc.perform(
                        get("/rest/players")
                                .param("order", filter.getOrder().name()))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("c"))
                .andExpect(jsonPath("$[0].title").value("testTitle"))
                .andExpect(jsonPath("$[0].race").value(Race.HUMAN.name()))
                .andExpect(jsonPath("$[0].profession").value(Profession.PALADIN.name()))
                .andExpect(jsonPath("$[0].birthday").value(1673481600000L))
                .andExpect(jsonPath("$[0].banned").value(false))
                .andExpect(jsonPath("$[0].experience").value(999))
                .andExpect(jsonPath("$[0].level").value(3))
                .andExpect(jsonPath("$[0].untilNextLevel").value(1))
                .andExpect(jsonPath("$[1].id").isNumber())
                .andExpect(jsonPath("$[1].name").value("a"))
                .andExpect(jsonPath("$[1].title").value("testTitle"))
                .andExpect(jsonPath("$[1].race").value(Race.HUMAN.name()))
                .andExpect(jsonPath("$[1].profession").value(Profession.PALADIN.name()))
                .andExpect(jsonPath("$[1].birthday").value(1673481600000L))
                .andExpect(jsonPath("$[1].banned").value(false))
                .andExpect(jsonPath("$[1].experience").value(1000))
                .andExpect(jsonPath("$[1].level").value(4))
                .andExpect(jsonPath("$[1].untilNextLevel").value(500))
                .andExpect(jsonPath("$[2].id").isNumber())
                .andExpect(jsonPath("$[2].name").value("b"))
                .andExpect(jsonPath("$[2].title").value("testTitle"))
                .andExpect(jsonPath("$[2].race").value(Race.HUMAN.name()))
                .andExpect(jsonPath("$[2].profession").value(Profession.PALADIN.name()))
                .andExpect(jsonPath("$[2].birthday").value(1673481600000L))
                .andExpect(jsonPath("$[2].banned").value(false))
                .andExpect(jsonPath("$[2].experience").value(1001))
                .andExpect(jsonPath("$[2].level").value(4))
                .andExpect(jsonPath("$[2].untilNextLevel").value(499));
    }

    @Test
    public void getAllPlayerByFilterTest() throws Exception {
        CreatePlayerRequest expectedPlayer1 = new CreatePlayerRequest();
        expectedPlayer1.setName("testPlayer");
        expectedPlayer1.setTitle("testTitle");
        expectedPlayer1.setRace(Race.HUMAN);
        expectedPlayer1.setProfession(Profession.PALADIN);
        expectedPlayer1.setExperience(1000);
        expectedPlayer1.setBirthday(1673481600000L);
        expectedPlayer1.setBanned(false);

        CreatePlayerRequest expectedPlayer2 = new CreatePlayerRequest();
        expectedPlayer2.setName("c");
        expectedPlayer2.setTitle("testTitle");
        expectedPlayer2.setRace(Race.HUMAN);
        expectedPlayer2.setProfession(Profession.PALADIN);
        expectedPlayer2.setExperience(999);
        expectedPlayer2.setBirthday(1673481600000L);
        expectedPlayer2.setBanned(false);

        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(expectedPlayer1));
        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(expectedPlayer2));

        mockMvc.perform(
                        get("/rest/players"))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("testPlayer"))
                .andExpect(jsonPath("$[0].title").value("testTitle"))
                .andExpect(jsonPath("$[0].race").value(Race.HUMAN.name()))
                .andExpect(jsonPath("$[0].profession").value(Profession.PALADIN.name()))
                .andExpect(jsonPath("$[0].birthday").value(1673481600000L))
                .andExpect(jsonPath("$[0].banned").value(false))
                .andExpect(jsonPath("$[0].experience").value(1000))
                .andExpect(jsonPath("$[0].level").value(4))
                .andExpect(jsonPath("$[0].untilNextLevel").value(500))
                .andExpect(jsonPath("$[1].id").isNumber())
                .andExpect(jsonPath("$[1].name").value("c"))
                .andExpect(jsonPath("$[1].title").value("testTitle"))
                .andExpect(jsonPath("$[1].race").value(Race.HUMAN.name()))
                .andExpect(jsonPath("$[1].profession").value(Profession.PALADIN.name()))
                .andExpect(jsonPath("$[1].birthday").value(1673481600000L))
                .andExpect(jsonPath("$[1].banned").value(false))
                .andExpect(jsonPath("$[1].experience").value(999))
                .andExpect(jsonPath("$[1].level").value(3))
                .andExpect(jsonPath("$[1].untilNextLevel").value(1));
    }

    @Test
    public void getPlayerByIdTest() throws Exception {
        CreatePlayerRequest expectedTestPlayer1 = new CreatePlayerRequest();
        expectedTestPlayer1.setName("testPlayer");
        expectedTestPlayer1.setTitle("testTitle");
        expectedTestPlayer1.setRace(Race.HUMAN);
        expectedTestPlayer1.setProfession(Profession.PALADIN);
        expectedTestPlayer1.setExperience(1000);
        expectedTestPlayer1.setBirthday(1673481600000L);
        expectedTestPlayer1.setBanned(false);

        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(expectedTestPlayer1));

        mockMvc.perform(
                        get("/rest/players/1")) //TODO проверяем именно созданного игрока, айди которого мы получем на шаге выше
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("testPlayer"))
                .andExpect(jsonPath("$.title").value("testTitle"))
                .andExpect(jsonPath("$.race").value(Race.HUMAN.name()))
                .andExpect(jsonPath("$.profession").value(Profession.PALADIN.name()))
                .andExpect(jsonPath("$.birthday").value(1673481600000L))
                .andExpect(jsonPath("$.banned").value(false))
                .andExpect(jsonPath("$.experience").value(1000))
                .andExpect(jsonPath("$.level").value(4))
                .andExpect(jsonPath("$.untilNextLevel").value(500));
    }

    @Test
    public void getAllPlayersCountTest() throws Exception {
        CreatePlayerRequest expectedTestPlayer1 = new CreatePlayerRequest();
        expectedTestPlayer1.setName("testPlayer");
        expectedTestPlayer1.setTitle("testTitle");
        expectedTestPlayer1.setRace(Race.HUMAN);
        expectedTestPlayer1.setProfession(Profession.PALADIN);
        expectedTestPlayer1.setExperience(1000);
        expectedTestPlayer1.setBirthday(1673481600000L);
        expectedTestPlayer1.setBanned(false);

        CreatePlayerRequest expectedTestPlayer2 = new CreatePlayerRequest();
        expectedTestPlayer2.setName("c");
        expectedTestPlayer2.setTitle("testTitle");
        expectedTestPlayer2.setRace(Race.HUMAN);
        expectedTestPlayer2.setProfession(Profession.PALADIN);
        expectedTestPlayer2.setExperience(999);
        expectedTestPlayer2.setBirthday(1673481600000L);
        expectedTestPlayer2.setBanned(false);

        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(expectedTestPlayer1));
        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(expectedTestPlayer2));

        mockMvc.perform(
                        get("/rest/players/count"))
                .andExpect(jsonPath("$.count").value(2));
    }

    @Test
    public void editPlayerTest() throws Exception {
        CreatePlayerRequest testPlayer1 = new CreatePlayerRequest();
        testPlayer1.setName("testPlayer");
        testPlayer1.setTitle("testTitle");
        testPlayer1.setRace(Race.HUMAN);
        testPlayer1.setProfession(Profession.PALADIN);
        testPlayer1.setExperience(1000);
        testPlayer1.setBirthday(1673481600000L);
        testPlayer1.setBanned(false);

        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(testPlayer1));

        EditPlayerRequest editPlayerRequest = new EditPlayerRequest();
        editPlayerRequest.setName("newNameForPlayer");
        editPlayerRequest.setTitle("newTitleForPlayer");
        editPlayerRequest.setBanned(true);
        editPlayerRequest.setRace(Race.GIANT);
        editPlayerRequest.setProfession(Profession.ROGUE);
        editPlayerRequest.setBirthday(1673481600043L);
        editPlayerRequest.setExperience(5000);

        mockMvc.perform(
                        post("/rest/players/1")
                                .content(objectMapper.writeValueAsString(editPlayerRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("newNameForPlayer"))
                .andExpect(jsonPath("$.title").value("newTitleForPlayer"))
                .andExpect(jsonPath("$.race").value(Race.GIANT.name()))
                .andExpect(jsonPath("$.profession").value(Profession.ROGUE.name()))
                .andExpect(jsonPath("$.birthday").value(1673481600043L))
                .andExpect(jsonPath("$.banned").value(true))
                .andExpect(jsonPath("$.experience").value(5000))
                .andExpect(jsonPath("$.level").value(9))
                .andExpect(jsonPath("$.untilNextLevel").value(500));
    }

    @Test
    public void deletePlayerTest() throws Exception {
        CreatePlayerRequest testPlayer1 = new CreatePlayerRequest();
        testPlayer1.setName("testPlayer");
        testPlayer1.setTitle("testTitle");
        testPlayer1.setRace(Race.HUMAN);
        testPlayer1.setProfession(Profession.PALADIN);
        testPlayer1.setExperience(1000);
        testPlayer1.setBirthday(1673481600000L);
        testPlayer1.setBanned(false);

        playerService.create(PlayerMapper.convertPlayerRequestToPlayerDto(testPlayer1));

        mockMvc.perform(
                        delete("/rest/players/50"))
                .andExpect(status().isOk());

        //TODO сделать прверку что он реально удалился
    }
}