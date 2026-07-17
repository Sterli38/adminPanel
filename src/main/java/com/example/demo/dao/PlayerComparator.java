package com.example.demo.dao;

import com.example.demo.entity.Player;
import com.example.demo.filter.Filter;
import com.example.demo.filter.PlayerOrder;

import java.util.Comparator;

public class PlayerComparator implements Comparator<Player> {
    private final Filter filter;

    PlayerComparator(Filter filter) {
        this.filter = filter;
    }

    @Override
    public int compare(Player a, Player b) {
        if (filter == null || filter.getOrder() == null) {
            return a.getId().compareTo(b.getId());
        }

        if (filter.getOrder() == PlayerOrder.ID) {
            return a.getId().compareTo(b.getId());
        } else if (filter.getOrder() == PlayerOrder.NAME) {
            return a.getName().compareToIgnoreCase(b.getName());
        } else if (filter.getOrder() == PlayerOrder.EXPERIENCE) {
            return a.getExperience().compareTo(b.getExperience());
        } else if (filter.getOrder() == PlayerOrder.BIRTHDAY) {
            return a.getBirthday().compareTo(b.getBirthday());
        } else if (filter.getOrder() == PlayerOrder.LEVEL) {
            return a.getLevel().compareTo(b.getLevel());
        }
        return 0;
    }
}
