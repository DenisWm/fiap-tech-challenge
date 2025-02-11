package com.fiap.tech.payment.application.receive;

import com.fiap.tech.payment.domain.PaymentStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record ReceivePaymentCommand(
        String paymentID,
        String orderID,
        String clientID,
        BigDecimal amount,
        Instant createdAt,
        PaymentStatus status
) {

    public static ReceivePaymentCommand with(final String paymentID,
                                             final String orderID,
                                             final String clientID,
                                             final BigDecimal amount,
                                             final Instant createdAt,
                                             final PaymentStatus status) {
        return new ReceivePaymentCommand(paymentID, orderID, clientID, amount, createdAt, status);
    }
}
