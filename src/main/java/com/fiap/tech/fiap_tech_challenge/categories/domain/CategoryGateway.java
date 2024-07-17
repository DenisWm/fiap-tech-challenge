package com.fiap.tech.fiap_tech_challenge.categories.domain;

import com.fiap.tech.fiap_tech_challenge.common.domain.pagination.Pagination;
import com.fiap.tech.fiap_tech_challenge.common.domain.pagination.SearchQuery;
import com.fiap.tech.fiap_tech_challenge.product.domain.Product;
import com.fiap.tech.fiap_tech_challenge.product.domain.pagination.ProductSearchQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryGateway {

    Optional<Category> findById(CategoryID id);
    boolean existsByIds(CategoryID id);
    Pagination<Category> findAll(SearchQuery aQuery);

}
