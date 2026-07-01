package com.example.demo;

import com.example.demo.controller.request.CreatePlayerRequest;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.service.PlayerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RpgTest {
    @Autowired
    private PlayerService playerService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

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

}