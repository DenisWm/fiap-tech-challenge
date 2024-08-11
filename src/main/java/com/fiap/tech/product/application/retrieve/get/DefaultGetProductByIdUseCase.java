package com.fiap.tech.product.application.retrieve.get;


import com.fiap.tech.product.domain.Product;
import com.fiap.tech.product.domain.ProductGateway;
import com.fiap.tech.product.domain.ProductID;

import java.util.Objects;

import static com.fiap.tech.common.application.utils.IDNotFoundUtils.notFound;


public class DefaultGetProductByIdUseCase extends GetProductByIdUseCase {

    private final ProductGateway productGateway;

    public DefaultGetProductByIdUseCase(final ProductGateway genreGateway) {
        this.productGateway = Objects.requireNonNull(genreGateway);
    }

    @Override
    public ProductOutput execute(String anId) {
        final var id = ProductID.from(anId);
        return productGateway.findById(id)
                .map(ProductOutput::from)
                .orElseThrow(notFound(id, Product.class));
    }
}
