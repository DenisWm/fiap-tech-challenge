package com.fiap.tech.categories.infra;


import com.fiap.tech.categories.domain.Category;
import com.fiap.tech.categories.domain.CategoryGateway;
import com.fiap.tech.categories.domain.CategoryID;
import com.fiap.tech.common.domain.pagination.Pagination;
import com.fiap.tech.common.domain.pagination.SearchQuery;
import com.fiap.tech.common.infra.utils.SpecificationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CategoryPostgresGateway implements CategoryGateway {

    private final CategoryRepository categoryRepository;

    public CategoryPostgresGateway(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<Category> findById(CategoryID id) {
        return this.categoryRepository.findById(id.getValue()).map(CategoryJpaEntity::toAggregate);
    }

    @Override
    public boolean existsByIds(CategoryID id) {
        return categoryRepository.existsById(id.getValue());
    }

    @Override
    public Pagination<Category> findAll(SearchQuery aQuery) {
        final var page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        final var where = Optional.ofNullable(aQuery.terms())
                .filter(str -> !str.isBlank())
                .map(this::assembleSpecification)
                .orElse(null);

        final var results = this.categoryRepository.findAll(Specification.where(where), page);
        return new Pagination<>(
                results.getNumber(),
                results.getSize(),
                results.getTotalElements(),
                results.map(CategoryJpaEntity::toAggregate).stream().toList()
        );
    }

    private Specification<CategoryJpaEntity> assembleSpecification(final String name) {
        return SpecificationUtils.like("name", name);
    }
}
