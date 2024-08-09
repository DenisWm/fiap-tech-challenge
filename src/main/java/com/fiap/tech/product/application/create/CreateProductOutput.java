package com.fiap.tech.product.application.create;


import com.fiap.tech.product.domain.Product;

public record CreateProductOutput(
        String id
) {
    public static CreateProductOutput from (final Product aProduct){
        return new CreateProductOutput(aProduct.getId().getValue());
    }

    public static CreateProductOutput from (final String anId){
        return new CreateProductOutput(anId);
    }
}
