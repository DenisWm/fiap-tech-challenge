package com.fiap.tech.order;

import com.fiap.tech.common.domain.pagination.Pagination;
import com.fiap.tech.configuration.annotations.OrderCreatedQueue;
import com.fiap.tech.order.domain.Order;
import com.fiap.tech.order.domain.OrderGateway;
import com.fiap.tech.order.domain.OrderID;
import com.fiap.tech.product.application.retrieve.list.OrderSearchQuery;
import com.fiap.tech.services.EventService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DefaultOrderGateway implements OrderGateway {

    private final EventService eventService;

    public DefaultOrderGateway(@OrderCreatedQueue EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public Order create(Order order) {
        return null;
    }

    @Override
    public Pagination<Order> findAll(OrderSearchQuery aQuery) {
        return null;
    }

    @Override
    public Optional<Order> findById(OrderID anId) {
        return Optional.empty();
    }

    @Override
    public Order update(Order anOrder) {
        return null;
    }

    //Método para salvar
}
