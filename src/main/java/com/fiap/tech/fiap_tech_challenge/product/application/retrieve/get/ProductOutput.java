package com.fiap.tech.fiap_tech_challenge.product.application.retrieve.get;

import com.fiap.tech.fiap_tech_challenge.product.domain.Product;
import com.fiap.tech.fiap_tech_challenge.product.domain.ProductID;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;


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
