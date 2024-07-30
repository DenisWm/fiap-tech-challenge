package com.fiap.tech.fiap_tech_challenge.order.domain;

import com.fiap.tech.fiap_tech_challenge.product.domain.Product;
import com.fiap.tech.fiap_tech_challenge.product.domain.ProductID;

import java.util.Optional;

public interface OrderGateway {
    Order create(Order order);

    Optional<Order> findById(OrderID anId);
}
