package com.fiap.tech.fiap_tech_challenge.order.domain;

import com.fiap.tech.fiap_tech_challenge.common.domain.Identifier;
import com.fiap.tech.fiap_tech_challenge.product.domain.ProductID;

import java.util.Objects;
import java.util.UUID;

public class OrderID extends Identifier {

    private String value;

    public OrderID(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static OrderID unique() {
        return OrderID.from(UUID.randomUUID());
    }

    public static OrderID from(final String anId) {
        return new OrderID(anId);
    }
    public static OrderID from (final UUID anId) {
        return new OrderID(anId.toString().toLowerCase());
    }

    @Override
    public String getValue() {
        return value;
    }
}
