package com.fiap.tech.payment.domain;

import com.fiap.tech.common.domain.AggregateRoot;
import com.fiap.tech.common.domain.validation.ValidationHandler;

import java.math.BigDecimal;
import java.time.Instant;

public class Payment extends AggregateRoot<PaymentID> {
    private final BigDecimal amount;
    private final Instant timestamp;
    private PaymentStatus status;

    public Payment(PaymentID paymentId, BigDecimal amount, Instant timestamp, PaymentStatus status) {
        super(paymentId);
        this.amount = amount;
        this.timestamp = timestamp;
        this.status = status;
    }

    public static Payment receivePayment(PaymentID paymentId, BigDecimal amount, Instant timestamp, PaymentStatus status) {
        return new Payment(paymentId, amount, timestamp, status);
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


    public static Payment with(PaymentID paymentID, BigDecimal amount, Instant timestamp, PaymentStatus status) {
        return new Payment(paymentID, amount, timestamp, status);
    }

    public static Payment with(Payment payment) {
        return with(payment.getId(), payment.getAmount(), payment.getTimestamp(), payment.getStatus());
    }

    public static Payment unidentified() {
        return new Payment(PaymentID.unique(), BigDecimal.ZERO, Instant.now(), PaymentStatus.UNIDENTIFIED);
    }


    @Override
    public void validate(ValidationHandler handler) {

    }

    @Override
    public String toString() {
        return "PaymentStatus = " +
                status;
    }
}