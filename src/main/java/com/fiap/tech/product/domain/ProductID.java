package com.fiap.tech.product.domain;

import com.fiap.tech.common.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class ProductID extends Identifier {

    private String value;

    public ProductID(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static ProductID unique() {
        return ProductID.from(UUID.randomUUID());
    }

    public static ProductID from(final String anId) {
        return new ProductID(anId);
    }
    public static ProductID from (final UUID anId) {
        return new ProductID(anId.toString().toLowerCase());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductID productID = (ProductID) o;

        return Objects.equals(value, productID.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
