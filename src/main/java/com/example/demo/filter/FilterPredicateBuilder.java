package com.example.demo.filter;

import com.example.demo.entity.Player;

import java.util.function.Predicate;

public class FilterPredicateBuilder {
    public static Predicate<Player> buildPredicate(Filter filter) {
        Predicate<Player> predicate = player -> true;

        if (filter == null) {
            return predicate;
        }

        if (filter.getName() != null) {
            predicate = predicate.and(player -> player.getName().toLowerCase().contains(filter.getName().toLowerCase()));
        }

        if (filter.getTitle() != null) {
            predicate = predicate.and(player -> player.getTitle().toLowerCase().contains(filter.getTitle().toLowerCase()));
        }

        if (filter.getRace() != null) {
            predicate = predicate.and(player -> player.getRace().equals(filter.getRace()));
        }

        if (filter.getProfession() != null) {
            predicate = predicate.and(player -> player.getProfession().equals(filter.getProfession()));
        }

        if (filter.getBefore() != null) {
            predicate = predicate.and(player -> player.getBirthday().getTime() <= filter.getBefore());
        }

        if (filter.getAfter() != null) {
            predicate = predicate.and(player -> player.getBirthday().getTime() >= filter.getAfter());
        }

        if (filter.getBanned() != null) {
            predicate = predicate.and(player -> player.getBanned().equals(filter.getBanned()));
        }

        if (filter.getMinExperience() != null) {
            predicate = predicate.and(player -> player.getExperience() >= filter.getMinExperience());
        }

        if (filter.getMaxExperience() != null) {
            predicate = predicate.and(player -> player.getExperience() <= filter.getMaxExperience());
        }

        if (filter.getMinLevel() != null) {
            predicate = predicate.and(player -> player.getLevel() >= filter.getMinLevel());
        }

        if (filter.getMaxLevel() != null) {
            predicate = predicate.and(player -> player.getLevel() <= filter.getMaxLevel());
        }

        return predicate;

    }
}
