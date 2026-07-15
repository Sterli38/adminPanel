package com.example.demo.dao;

import com.example.demo.filter.Filter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SqlBuilder {
    private final List<Object> values = new ArrayList<>();

    public StringBuilder buildCondition(Filter filter) {
        List<String> clauses = new ArrayList<>();

        if (filter.getName() != null) {
            clauses.add("player.name ILIKE ?");
            values.add("%" + filter.getName() + "%");
        }

        if (filter.getTitle() != null) {
            clauses.add("title ILIKE ?");
            values.add("%" + filter.getTitle() + "%");
        }

        if (filter.getRace() != null) {
            clauses.add("race.name = ?");
            values.add(filter.getRace().name());
        }

        if (filter.getProfession() != null) {
            clauses.add("profession.name = ?");
            values.add(filter.getProfession().name());
        }

        if (filter.getBefore() != null) {
            clauses.add("birthday <= ?");
            values.add(new Date(filter.getBefore()));
        }

        if (filter.getAfter() != null) {
            clauses.add("birthday >= ?");
            values.add(new Date(filter.getAfter()));
        }

        if (filter.getBanned() != null) {
            clauses.add("banned = ?");
            values.add(String.valueOf(filter.getBanned()));
        }

        if (filter.getMinExperience() != null) {
            clauses.add("experience >= ?");
            values.add(String.valueOf(filter.getMinExperience()));
        }

        if (filter.getMaxExperience() != null) {
            clauses.add("experience <= ?");
            values.add(String.valueOf(filter.getMaxExperience()));
        }

        if (filter.getMinLevel() != null) {
            clauses.add("level >= ?");
            values.add(String.valueOf(filter.getMinLevel()));
        }

        if (filter.getMaxLevel() != null) {
            clauses.add("level <= ?");
            values.add(String.valueOf(filter.getMaxLevel()));
        }

        StringBuilder stringBuilder = new StringBuilder();

        if (!clauses.isEmpty()) {
            stringBuilder.append(" WHERE ");
            stringBuilder.append(String.join(" AND ", clauses));
        }

        return stringBuilder;
    }

    public List<Object> getValues() {
        return values;
    }
}
