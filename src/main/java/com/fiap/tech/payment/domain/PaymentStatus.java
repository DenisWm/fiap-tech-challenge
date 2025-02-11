package com.fiap.tech.payment.domain;

import com.fiap.tech.common.domain.ValueObject;
import com.fiap.tech.order.domain.OrderStatus;
import lombok.Getter;

import java.util.Objects;

@Getter
public final class PaymentStatus extends ValueObject {
    private final String value;

    private PaymentStatus(String value) {
        this.value = value;
    }

    public static final PaymentStatus APPROVED = new PaymentStatus("Approved");
    public static final PaymentStatus FAILED = new PaymentStatus("Failed");
    public static final PaymentStatus UNIDENTIFIED = new PaymentStatus("Unidentified");

    public static PaymentStatus valueOf(String status) {
        return new PaymentStatus(status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentStatus that = (PaymentStatus) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "PaymentStatus{" + "status='" + value + '\'' + '}';
    }
}


