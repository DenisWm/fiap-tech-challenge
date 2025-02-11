package com.fiap.tech.payment.application.receive;

import com.fiap.tech.payment.domain.Payment;
import com.fiap.tech.payment.domain.PaymentID;

public record ReceivePaymentOutput(
        String id
) {

    public static ReceivePaymentOutput from(final Payment payment) {
        return new ReceivePaymentOutput(payment.getId().getValue());
    }
}
