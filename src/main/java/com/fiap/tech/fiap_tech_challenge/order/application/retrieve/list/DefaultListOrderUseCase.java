package com.fiap.tech.fiap_tech_challenge.order.application.retrieve.list;

import com.fiap.tech.fiap_tech_challenge.common.domain.pagination.Pagination;
import com.fiap.tech.fiap_tech_challenge.common.domain.pagination.SearchQuery;
import com.fiap.tech.fiap_tech_challenge.order.domain.OrderGateway;
import com.fiap.tech.fiap_tech_challenge.product.application.retrieve.list.OrderSearchQuery;
import com.fiap.tech.fiap_tech_challenge.product.application.retrieve.list.ProductListOutput;

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
