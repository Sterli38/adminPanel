package com.example.demo.filter;

import com.example.demo.entity.Player;

import java.util.Objects;
import java.util.function.Predicate;

public class FilterPredicateBuilder {
    public static Predicate<Player> buildPredicate(Filter filter) {
        Predicate<Player> predicate = player -> true;

        if (filter.getName() != null) {
            predicate = predicate.and(player -> Objects.equals(filter.getName(), player.getName()));
        }
        return null;
    }
}
