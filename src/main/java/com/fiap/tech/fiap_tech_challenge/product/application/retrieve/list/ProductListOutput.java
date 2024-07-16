package com.fiap.tech.fiap_tech_challenge.product.application.retrieve.list;



import com.fiap.tech.fiap_tech_challenge.product.domain.Product;
import com.fiap.tech.fiap_tech_challenge.product.domain.ProductID;

import java.math.BigDecimal;
import java.time.Instant;

public record ProductListOutput(
        ProductID id,
        String name,
        String description,
        BigDecimal price,
        String categoryId

) {

    public static ProductListOutput from(final Product aProduct) {
        return new ProductListOutput(aProduct.getId(),
                aProduct.getName(),
                aProduct.getDescription(),
                aProduct.getPrice(),
                aProduct.getCategory().getValue()
        );
    }
}
