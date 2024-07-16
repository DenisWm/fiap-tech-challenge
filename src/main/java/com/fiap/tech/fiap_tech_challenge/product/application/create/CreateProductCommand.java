package com.fiap.tech.fiap_tech_challenge.product.application.create;

import java.math.BigDecimal;

public record CreateProductCommand(
        String name,
        String description,
        BigDecimal price,
        String categoryID

) {
    public static CreateProductCommand with(final String aName, final String aDescription, final BigDecimal aPrice, final String aCategoryID) {
        return new CreateProductCommand(aName, aDescription, aPrice, aCategoryID);
    }
}
