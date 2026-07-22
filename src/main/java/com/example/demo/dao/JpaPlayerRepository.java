package com.example.demo.dao;

import com.example.demo.entity.Player;
import com.example.demo.filter.Filter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface JpaPlayerRepository extends JpaRepository<Player, Long>, JpaSpecificationExecutor<Player> {

    default List<Player> getPlayers(Filter filter) {
        Specification<Player> spec = buildSpecification(filter);
        Sort sort = buildSort(filter);

        int page = filter.getPageNumber() != null ? filter.getPageNumber() : 0;
        int size = filter.getPageSize() != null ? filter.getPageSize() : 3;
        Pageable pageable = PageRequest.of(page, size, sort);

        return findAll(spec, pageable).getContent();
    }

    default Integer getAllPlayersCount(Filter filter) {
        Specification<Player> spec = buildSpecification(filter);
        return (int) count(spec);
    }

    private Specification<Player> buildSpecification(Filter filter) { //TODO вынести в отдельный класс
        return (root, query, criteriaBuilder) -> {
            var predicate = criteriaBuilder.conjunction(); //TODO убарть var

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

    private Sort buildSort(Filter filter) {
        if (filter.getOrder() == null) {
            return Sort.by(Sort.Direction.ASC, "id");
        }
        return Sort.by(Sort.Direction.ASC, filter.getOrder().getFieldName());
    }
}
