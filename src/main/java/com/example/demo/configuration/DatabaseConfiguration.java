package com.example.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {
    @Bean
    public SimpleJdbcInsert simplePlayerJdbcInsert(DataSource dataSource) {
        return new SimpleJdbcInsert(dataSource)
                .withTableName("player")
                .usingGeneratedKeyColumns("id");
    }
}
