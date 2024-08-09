package com.fiap.tech.categories.domain;

import com.fiap.tech.common.domain.pagination.Pagination;
import com.fiap.tech.common.domain.pagination.SearchQuery;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryGateway {

    Optional<Category> findById(CategoryID id);
    boolean existsByIds(CategoryID id);
    Pagination<Category> findAll(SearchQuery aQuery);

}
