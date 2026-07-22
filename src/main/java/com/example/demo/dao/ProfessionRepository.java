package com.example.demo.dao;

import com.example.demo.entity.ProfessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessionRepository extends JpaRepository<ProfessionEntity, Long> {
    ProfessionEntity getByName(String name);

}
