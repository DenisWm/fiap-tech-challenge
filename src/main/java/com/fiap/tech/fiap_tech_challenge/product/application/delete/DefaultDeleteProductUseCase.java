package com.fiap.tech.fiap_tech_challenge.product.application.delete;



import com.fiap.tech.fiap_tech_challenge.product.domain.ProductGateway;
import com.fiap.tech.fiap_tech_challenge.product.domain.ProductID;

import java.util.Objects;

public class DefaultDeleteProductUseCase extends DeleteProductUseCase {
    private final ProductGateway productGateway;

    public DefaultDeleteProductUseCase(final ProductGateway productGateway) {
        this.productGateway = Objects.requireNonNull(productGateway);
    }

    @Override
    public void execute(String anId) {
        productGateway.deleteById(ProductID.from(anId));
    }
}
