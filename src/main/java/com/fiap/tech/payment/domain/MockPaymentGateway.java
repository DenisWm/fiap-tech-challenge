package com.fiap.tech.payment.domain;

import com.fiap.tech.order.domain.Order;
import com.fiap.tech.order.domain.OrderID;

import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

public class MockPaymentGateway implements PaymentGateway {
    private final Map<PaymentID, Payment> payments = new HashMap<>();
    private final Map<OrderID, Order> orders = new HashMap<>();

    @Override
    public Payment create(Payment payment) {
        payments.put(payment.getId(), payment);
        return payment;
    }

    @Override
    public Optional<Payment> findById(PaymentID anId) {
        return Optional.ofNullable(payments.get(anId));
    }

    @Override
    public Optional<Order> findOrderById(OrderID orderId) {
        return Optional.ofNullable(orders.get(orderId));
    }
}