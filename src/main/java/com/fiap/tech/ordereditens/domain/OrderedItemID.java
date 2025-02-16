package com.fiap.tech.ordereditens.domain;

import com.fiap.tech.common.domain.Identifier;
import com.fiap.tech.common.domain.ValueObject;

import java.util.Objects;
import java.util.UUID;

public class OrderedItemID extends Identifier {

    private String value;

    private OrderedItemID(String value) {
        this.value = value;
    }

    public static OrderedItemID unique() {
        return new OrderedItemID(UUID.randomUUID().toString());
    }
    public static OrderedItemID from(final String id) {
        return new OrderedItemID(id);
    }

    public static OrderedItemID from(final UUID id) {
        return from(id.toString());
    }

    public String getValue() {
        return value;
    }
}
