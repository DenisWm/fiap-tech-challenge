package com.fiap.tech.order.application.retrieve.get;


import com.fiap.tech.common.application.utils.IDNotFoundUtils;
import com.fiap.tech.order.domain.Order;
import com.fiap.tech.order.domain.OrderGateway;
import com.fiap.tech.order.domain.OrderID;
import com.fiap.tech.product.domain.Product;
import com.fiap.tech.product.domain.ProductGateway;
import com.fiap.tech.product.domain.ProductID;

import java.util.List;
import java.util.Objects;


public class DefaultGetOrderByIdUseCase extends GetOrderByIdUseCase {

    private final ProductGateway productGateway;
    private final OrderGateway orderGateway;

    public DefaultGetOrderByIdUseCase(final ProductGateway productGateway, final OrderGateway orderGateway) {
        this.productGateway = Objects.requireNonNull(productGateway);
        this.orderGateway = Objects.requireNonNull(orderGateway);
    }

    @Override
    public OrderOutput execute(String anId) {
        final var id = OrderID.from(anId);

        Order order = orderGateway.findById(id)
                .orElseThrow(IDNotFoundUtils.notFound(id, Order.class));

        List<Product> products = productGateway.findByIds(order.getProducts()
                .stream().map(ProductID::getValue ).toList());

        final var orderOutput = OrderOutput.from(order).withProducts(products.stream().map(
                OrderOutput.OrderProductOutput::from).toList());
        return orderOutput;

    }
}
