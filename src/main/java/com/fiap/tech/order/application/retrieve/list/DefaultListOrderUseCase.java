package com.fiap.tech.order.application.retrieve.list;

import com.fiap.tech.common.domain.pagination.Pagination;
import com.fiap.tech.order.domain.OrderGateway;
import com.fiap.tech.product.application.retrieve.list.OrderSearchQuery;

import java.util.Objects;

public class DefaultListOrderUseCase extends ListOrderUseCase {

    private final OrderGateway orderGateway;

    public DefaultListOrderUseCase(final OrderGateway orderGateway) {
        this.orderGateway = Objects.requireNonNull(orderGateway);
    }

    @Override
    public Pagination<OrderListOutput> execute(OrderSearchQuery aQuery) {
        return this.orderGateway.findAll(aQuery)
                .map(OrderListOutput::from);
    }
}
