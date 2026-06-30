package com.example.demo;

import com.example.demo.service.PlayerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class RpgTest {
    @Autowired
    private PlayerService playerService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test() {
        int res = sum(5, 5);
        Assertions.assertEquals(10, res);
    }

    private int sum(int a, int b) {
        return a + b;
    }

}