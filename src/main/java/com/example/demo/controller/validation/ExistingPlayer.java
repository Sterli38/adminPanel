package com.example.demo.controller.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PlayerExistsValidator.class)
public @interface ExistingPlayer {
    String message() default "Игрок с таким ID не найден";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
