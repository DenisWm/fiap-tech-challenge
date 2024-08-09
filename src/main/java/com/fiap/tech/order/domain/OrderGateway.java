package com.fiap.tech.order.domain;

import com.fiap.tech.common.domain.pagination.Pagination;
import com.fiap.tech.product.application.retrieve.list.OrderSearchQuery;

import java.util.Optional;

public interface OrderGateway {
    Order create(Order order);
    Pagination<Order> findAll(OrderSearchQuery aQuery);

    Optional<Order> findById(OrderID anId);
}
