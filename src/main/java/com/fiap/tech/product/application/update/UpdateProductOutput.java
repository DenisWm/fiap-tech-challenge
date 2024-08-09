package com.fiap.tech.product.application.update;


import com.fiap.tech.product.domain.Product;

public record UpdateProductOutput(
        String id
) {
    public static UpdateProductOutput from (final Product aProduct){
        return new UpdateProductOutput(aProduct.getId().getValue());
    }

    public static UpdateProductOutput from (final String anId){
        return new UpdateProductOutput(anId);
    }
}
