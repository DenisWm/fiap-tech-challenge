package com.fiap.tech.payment.domain;

import com.fiap.tech.order.domain.Order;
import com.fiap.tech.order.domain.OrderID;

import java.util.Optional;

public interface PaymentGateway {
    Payment create(Payment payment);

    Optional<Payment> findById(PaymentID anId);

    Optional<Order> findOrderById(OrderID orderId);
}
