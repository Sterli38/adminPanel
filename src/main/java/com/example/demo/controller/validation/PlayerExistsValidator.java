package com.example.demo.controller.validation;

import com.example.demo.controller.exception.PlayerNotFoundException;
import com.example.demo.dao.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class PlayerExistsValidator implements ConstraintValidator<ExistingPlayer, Long> {
    private final PlayerRepository playerRepository;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {
        if (id == null || id < 0) {
            return true;
        }
        if (playerRepository.getPlayerById(id) == null) {
            throw new PlayerNotFoundException("Игрок с id: " + id + " не найден");
        }
        return true;
    }
}
