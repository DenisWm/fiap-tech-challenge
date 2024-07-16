package com.fiap.tech.fiap_tech_challenge.product.application.create;


import com.fiap.tech.fiap_tech_challenge.product.domain.Product;

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
