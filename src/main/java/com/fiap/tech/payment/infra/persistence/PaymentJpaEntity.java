package com.fiap.tech.payment.infra.persistence;

import com.fiap.tech.payment.domain.Payment;
import com.fiap.tech.payment.domain.PaymentID;
import com.fiap.tech.payment.domain.PaymentStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity(name = "Payments")
@Table(name = "payments")
public class PaymentJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;
    private BigDecimal amount;
    private Instant timestamp;
    private String status;

    public PaymentJpaEntity(String id, BigDecimal amount, Instant timestamp, String status) {
        this.id = id;
        this.amount = amount;
        this.timestamp = timestamp;
        this.status = status;
    }


    public PaymentJpaEntity() {

    }

    public static PaymentJpaEntity from(final Payment payment) {
        final var entity = new PaymentJpaEntity(
                payment.getId().getValue(),
                payment.getAmount(),
                payment.getTimestamp(),
                payment.getStatus().getValue()
        );

        return entity;
    }

    public Payment toAggregate() {
        return Payment.with(
                PaymentID.from(this.id),
                this.amount,
                this.timestamp,
                this.status != null ? PaymentStatus.valueOf(this.status) : null

        );
    }

    public String getId() {
        return id;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public BigDecimal getAmount() {
        return amount;    }

}

