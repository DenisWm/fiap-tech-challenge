package com.fiap.tech.order.application.retrieve.get;


import com.fiap.tech.client.domain.Client;
import com.fiap.tech.client.domain.ClientGateway;
import com.fiap.tech.common.application.utils.IDNotFoundUtils;
import com.fiap.tech.order.domain.Order;
import com.fiap.tech.order.domain.OrderGateway;
import com.fiap.tech.order.domain.OrderID;
import com.fiap.tech.ordereditens.domain.OrderedItem;
import com.fiap.tech.ordereditens.domain.OrderedItemGateway;
import com.fiap.tech.ordereditens.domain.OrderedItemID;
import com.fiap.tech.product.domain.Product;
import com.fiap.tech.product.domain.ProductGateway;

import java.util.List;
import java.util.Objects;


public class DefaultGetOrderByIdUseCase extends GetOrderByIdUseCase {

    private final ProductGateway productGateway;
    private final OrderedItemGateway orderedItemGateway;
    private final OrderGateway orderGateway;
    private final ClientGateway clientGateway;

    public DefaultGetOrderByIdUseCase(final ProductGateway productGateway, final OrderedItemGateway orderedItemGateway, final OrderGateway orderGateway, final ClientGateway clientGateway) {
        this.productGateway = Objects.requireNonNull(productGateway);
        this.orderedItemGateway = Objects.requireNonNull(orderedItemGateway);
        this.orderGateway = Objects.requireNonNull(orderGateway);
        this.clientGateway = Objects.requireNonNull(clientGateway);
    }

    @Override
    public OrderOutput execute(String anId) {
        final var id = OrderID.from(anId);

        Order order = orderGateway.findById(id)
                .orElseThrow(IDNotFoundUtils.notFound(id, Order.class));

        List<OrderedItem> orderedItems = orderedItemGateway.findByIds(order.getOrderedItems()
                .stream().map(OrderedItemID::getValue).toList());

        List<Product> products = productGateway.findByIds(orderedItems.stream()
                .map(orderedItem -> orderedItem.getProduct().getValue()).toList());

        Client client = clientGateway.findById(order.getClientId())
                .orElse(null);

        final var orderOutput = OrderOutput.from(order).withOrderedItemsProductsAndClient(orderedItems, products, client);
        return orderOutput;
    }
}
