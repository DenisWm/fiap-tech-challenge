package com.fiap.tech.payment.domain;

import com.fiap.tech.common.domain.Identifier;
import java.util.Objects;
import java.util.UUID;

public class PaymentID extends Identifier {
    private final String value;

    private PaymentID(String value) {
        this.value = value;
    }

    public static PaymentID unique() {
        return new PaymentID(UUID.randomUUID().toString());
    }

    public static PaymentID from(final String id) {
        return new PaymentID(id);
    }


    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentID that = (PaymentID) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}