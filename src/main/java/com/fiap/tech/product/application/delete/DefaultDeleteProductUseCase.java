package com.fiap.tech.product.application.delete;



import com.fiap.tech.product.domain.ProductGateway;
import com.fiap.tech.product.domain.ProductID;

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
