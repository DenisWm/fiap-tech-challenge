package com.fiap.tech.fiap_tech_challenge.categories.application.retrieve.list;

import com.fiap.tech.fiap_tech_challenge.categories.domain.CategoryGateway;
import com.fiap.tech.fiap_tech_challenge.common.domain.pagination.Pagination;
import com.fiap.tech.fiap_tech_challenge.common.domain.pagination.SearchQuery;
import com.fiap.tech.fiap_tech_challenge.product.application.retrieve.list.ListProductsUseCase;
import com.fiap.tech.fiap_tech_challenge.product.application.retrieve.list.ProductListOutput;
import com.fiap.tech.fiap_tech_challenge.product.domain.ProductGateway;
import com.fiap.tech.fiap_tech_challenge.product.domain.pagination.ProductSearchQuery;

import java.util.Objects;

public class DefaultListCategoryUseCase extends ListCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultListCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Pagination<CategoryListOutput> execute(final SearchQuery aQuery) {
        return this.categoryGateway.findAll(aQuery)
                .map(CategoryListOutput::from);
    }
}
