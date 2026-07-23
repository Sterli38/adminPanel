package com.example.demo.dao;

import com.example.demo.entity.Player;
import com.example.demo.filter.Filter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.Date;

public class PlayerJpaSpecification {
    public static Specification<Player> buildSpecification(Filter filter) { //TODO вынести в отдельный класс
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction(); //TODO убарть var

            if (filter.getName() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + filter.getName().toLowerCase() + "%"));
            }
            if (filter.getTitle() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + filter.getTitle().toLowerCase() + "%"));
            }
            if (filter.getRace() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("race").get("name"), filter.getRace().name()));
            }
            if (filter.getProfession() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("profession").get("name"), filter.getProfession().name()));
            }
            if (filter.getBefore() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.lessThanOrEqualTo(root.get("birthday"), new Date(filter.getBefore())));
            }
            if (filter.getAfter() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.greaterThanOrEqualTo(root.get("birthday"), new Date(filter.getAfter())));
            }
            if (filter.getBanned() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.equal(root.get("banned"), filter.getBanned()));
            }
            if (filter.getMinExperience() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.ge(root.get("experience"), filter.getMinExperience()));
            }
            if (filter.getMaxExperience() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.le(root.get("experience"), filter.getMaxExperience()));
            }
            if (filter.getMinLevel() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.ge(root.get("level"), filter.getMinLevel()));
            }
            if (filter.getMaxLevel() != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.le(root.get("level"), filter.getMaxLevel()));
            }

            return predicate;
        };
    }
}
