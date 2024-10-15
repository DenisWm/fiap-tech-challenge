package com.fiap.tech.payment.domain;

import com.fiap.tech.common.domain.ValueObject;
import java.util.Objects;

public class PaymentStatus extends ValueObject {
    private final String value;

    private PaymentStatus(String value) {
        this.value = value;
    }

    public static PaymentStatus of(String status) {
        return new PaymentStatus(status);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentStatus that = (PaymentStatus) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
