package com.fiap.tech.product.application.retrieve.get;

import com.fiap.tech.product.domain.Product;
import com.fiap.tech.product.domain.ProductID;

import java.math.BigDecimal;


public record ProductOutput(
        ProductID id,
        String name,
        String description,
        BigDecimal price,
        String categoryId
) {

    public static ProductOutput from(final Product aProduct) {
        return new ProductOutput(
                aProduct.getId(),
                aProduct.getName(),
                aProduct.getDescription(),
                aProduct.getPrice(),
                aProduct.getCategory().getValue()
        );
    }
}
