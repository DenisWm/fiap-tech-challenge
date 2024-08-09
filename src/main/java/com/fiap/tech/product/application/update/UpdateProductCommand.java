package com.fiap.tech.product.application.update;

import java.math.BigDecimal;

public record UpdateProductCommand(
        String id,
        String name,
        String description,
        BigDecimal price,
        String categoryID

) {
    public static UpdateProductCommand with(final String anId, final String aName, final String aDescription, final BigDecimal aPrice, final String aCategoryID) {
        return new UpdateProductCommand(anId, aName, aDescription, aPrice, aCategoryID);
    }
}
