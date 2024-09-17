package com.fiap.tech.payment.domain;

import com.fiap.tech.common.domain.Entity;
import com.fiap.tech.common.domain.validation.ValidationHandler;

import java.math.BigDecimal;
import java.time.Instant;

public class Payment extends Entity<PaymentID>{
    private final BigDecimal amount;
    private final Instant timestamp;
    private PaymentStatus status;

    public Payment(PaymentID paymentId, BigDecimal amount, Instant timestamp, PaymentStatus status) {
        super(paymentId);
        this.amount = amount;
        this.timestamp = timestamp;
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    @Override
    public void validate(ValidationHandler handler) {

    }
}