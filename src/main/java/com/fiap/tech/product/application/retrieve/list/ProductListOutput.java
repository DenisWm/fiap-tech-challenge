package com.fiap.tech.product.application.retrieve.list;



import com.fiap.tech.product.domain.Product;
import com.fiap.tech.product.domain.ProductID;

import java.math.BigDecimal;

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
                aProduct.getCategory()
                        .getValue()
        );
    }
}
