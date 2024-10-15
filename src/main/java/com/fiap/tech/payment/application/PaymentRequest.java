package com.fiap.tech.payment.application;

import com.fiap.tech.order.domain.Order;
import com.fiap.tech.payment.domain.PaymentGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentRequest {

    private final PaymentUseCase paymentUseCase;

    @Autowired
    public PaymentRequest(PaymentUseCase paymentUseCase) {
        this.paymentUseCase = paymentUseCase;
    }

    public void sendPaymentRequest(Order order) {
        paymentUseCase.processPayment(order);
    }
}
