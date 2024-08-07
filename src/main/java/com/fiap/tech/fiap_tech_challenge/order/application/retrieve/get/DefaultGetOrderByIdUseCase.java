package com.fiap.tech.fiap_tech_challenge.order.application.retrieve.get;


import com.fiap.tech.fiap_tech_challenge.order.domain.Order;
import com.fiap.tech.fiap_tech_challenge.order.domain.OrderGateway;
import com.fiap.tech.fiap_tech_challenge.order.domain.OrderID;
import com.fiap.tech.fiap_tech_challenge.product.domain.Product;
import com.fiap.tech.fiap_tech_challenge.product.domain.ProductGateway;
import com.fiap.tech.fiap_tech_challenge.product.domain.ProductID;

import java.util.List;
import java.util.Objects;

import static com.fiap.tech.fiap_tech_challenge.common.application.utils.IDNotFoundUtils.notFound;


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
                .orElseThrow(notFound(id, Order.class));

        List<Product> products = productGateway.findByIds(order.getProducts()
                .stream().map(ProductID::getValue ).toList());

        final var orderOutput = OrderOutput.from(order).withProducts(products.stream().map(
                OrderOutput.OrderProductOutput::from).toList());
        return orderOutput;

    }
}
