package com.fiap.tech.payment.domain;

import com.fiap.tech.client.domain.ClientID;
import com.fiap.tech.common.domain.Entity;
import com.fiap.tech.common.domain.validation.ValidationHandler;
import com.fiap.tech.order.domain.Order;
import com.fiap.tech.order.domain.OrderID;
import com.fiap.tech.order.domain.OrderStatus;
import com.fiap.tech.ordereditens.domain.OrderedItemID;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

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


    public static Payment with(PaymentID paymentID, BigDecimal amount, Instant timestamp, PaymentStatus status){
        return new Payment(paymentID, amount, timestamp, status);
    }

    public static Payment with(Payment payment){
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