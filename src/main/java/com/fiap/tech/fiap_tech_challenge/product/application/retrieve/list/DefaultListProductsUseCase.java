package com.fiap.tech.fiap_tech_challenge.product.application.retrieve.list;

import com.fiap.tech.fiap_tech_challenge.common.domain.pagination.Pagination;
import com.fiap.tech.fiap_tech_challenge.common.domain.pagination.SearchQuery;
import com.fiap.tech.fiap_tech_challenge.product.domain.ProductGateway;

import java.util.Objects;

public class DefaultListProductsUseCase extends ListProductsUseCase {

    private final ProductGateway productGateway;

    public DefaultListProductsUseCase(final ProductGateway productGateway) {
        this.productGateway = Objects.requireNonNull(productGateway);
    }

    @Override
    public Pagination<ProductListOutput> execute(final SearchQuery aQuery) {
        return this.productGateway.findAll(aQuery)
                .map(ProductListOutput::from);
    }
}
