package com.fiap.tech.client.domain;

import com.fiap.tech.common.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class ClientID extends Identifier {

    private String value;

    public ClientID(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static ClientID unique() {
        return ClientID.from(UUID.randomUUID());
    }

    public static ClientID from(final String anId) {
        return new ClientID(anId);
    }
    public static ClientID from (final UUID anId) {
        return new ClientID(anId.toString().toLowerCase());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientID clientID = (ClientID) o;

        return value.equals(clientID.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
