package com.fiap.tech.payment.domain;

import com.fiap.tech.common.domain.ValueObject;
import com.fiap.tech.order.domain.OrderStatus;

import java.util.Objects;

public class PaymentStatus extends ValueObject {
    private final String value;

    private PaymentStatus(String value) {
        this.value = value;
    }

    // Payment has been initiated but not yet completed
    public static final PaymentStatus PENDING = new PaymentStatus("Pending");

    // Payment has been successfully approved
    public static final PaymentStatus APPROVED = new PaymentStatus("Approved");

    // Payment has been rejected
    public static final PaymentStatus REJECTED = new PaymentStatus("Rejected");

    // Payment failed due to an error
    public static final PaymentStatus FAILED = new PaymentStatus("Failed");

    // Payment has been cancelled by the user or system
    public static final PaymentStatus CANCELLED = new PaymentStatus("Cancelled");

    // Payment has been refunded to the user
    public static final PaymentStatus REFUNDED = new PaymentStatus("Refunded");

    // Payment is under review for security or compliance reasons
    public static final PaymentStatus IN_REVIEW = new PaymentStatus("In Review");

    // Payment has been completed and confirmed
    public static final PaymentStatus COMPLETED = new PaymentStatus("Completed");

    // Payment has expired due to inactivity within a specific time frame
    public static final PaymentStatus EXPIRED = new PaymentStatus("Expired");

    public static PaymentStatus valueOf(String status) {
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

    @Override
    public String toString() {
        return "PaymentStatus{" +
                "status='" + value + '\'' +
                '}';
    }
}


