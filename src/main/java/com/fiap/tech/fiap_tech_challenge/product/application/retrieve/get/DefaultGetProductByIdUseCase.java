package com.fiap.tech.fiap_tech_challenge.product.application.retrieve.get;


import com.fiap.tech.fiap_tech_challenge.product.domain.Product;
import com.fiap.tech.fiap_tech_challenge.product.domain.ProductGateway;
import com.fiap.tech.fiap_tech_challenge.product.domain.ProductID;

import java.util.Objects;

import static com.fiap.tech.fiap_tech_challenge.common.application.utils.IDNotFoundUtils.notFound;


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
