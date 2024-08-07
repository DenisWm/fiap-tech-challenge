package com.fiap.tech.fiap_tech_challenge.order.domain;

import com.fiap.tech.fiap_tech_challenge.common.domain.pagination.Pagination;
import com.fiap.tech.fiap_tech_challenge.common.domain.pagination.SearchQuery;
import com.fiap.tech.fiap_tech_challenge.product.application.retrieve.list.OrderSearchQuery;
import com.fiap.tech.fiap_tech_challenge.product.domain.Product;
import com.fiap.tech.fiap_tech_challenge.product.domain.ProductID;
import com.fiap.tech.fiap_tech_challenge.product.domain.pagination.ProductSearchQuery;

import java.util.Optional;

public interface OrderGateway {
    Order create(Order order);
    Pagination<Order> findAll(OrderSearchQuery aQuery);

    Optional<Order> findById(OrderID anId);
}
