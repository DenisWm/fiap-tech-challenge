package com.fiap.tech.product.application.retrieve.list;

import com.fiap.tech.common.domain.pagination.Pagination;
import com.fiap.tech.product.domain.ProductGateway;
import com.fiap.tech.product.domain.pagination.ProductSearchQuery;

import java.util.Objects;

public class DefaultListProductsUseCase extends ListProductsUseCase {

    private final ProductGateway productGateway;

    public DefaultListProductsUseCase(final ProductGateway productGateway) {
        this.productGateway = Objects.requireNonNull(productGateway);
    }

    @Override
    public Pagination<ProductListOutput> execute(final ProductSearchQuery aQuery) {
        return this.productGateway.findAll(aQuery)
                .map(ProductListOutput::from);
    }
}
