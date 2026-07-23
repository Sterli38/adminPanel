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

import java.util.List;

@Repository
public interface JpaPlayerRepository extends JpaRepository<Player, Long>, JpaSpecificationExecutor<Player> {
    default List<Player> getPlayers(Filter filter) {
        Specification<Player> spec = PlayerJpaSpecification.buildSpecification(filter);
        Sort sort = buildSort(filter);

        int page = filter.getPageNumber() != null ? filter.getPageNumber() : 0;
        int size = filter.getPageSize() != null ? filter.getPageSize() : 3;
        Pageable pageable = PageRequest.of(page, size, sort);

        return findAll(spec, pageable).getContent();
    }

    default Integer getAllPlayersCount(Filter filter) {
        Specification<Player> spec = PlayerJpaSpecification.buildSpecification(filter);
        return (int) count(spec);
    }

    private Sort buildSort(Filter filter) {
        if (filter.getOrder() == null) {
            return Sort.by(Sort.Direction.ASC, "id");
        }
        return Sort.by(Sort.Direction.ASC, filter.getOrder().getFieldName());
    }
}
