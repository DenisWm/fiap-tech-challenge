package com.fiap.tech.order.application.create;

import com.fiap.tech.client.domain.ClientGateway;
import com.fiap.tech.client.domain.ClientID;
import com.fiap.tech.common.domain.exceptions.NotificationException;
import com.fiap.tech.common.domain.validation.Error;
import com.fiap.tech.common.domain.validation.ValidationHandler;
import com.fiap.tech.common.domain.validation.handler.Notification;
import com.fiap.tech.order.domain.Order;
import com.fiap.tech.order.domain.OrderGateway;
import com.fiap.tech.order.domain.event.OrderCreated;
import com.fiap.tech.ordereditens.domain.OrderedItem;
import com.fiap.tech.ordereditens.domain.OrderedItemGateway;
import com.fiap.tech.product.domain.Product;
import com.fiap.tech.product.domain.ProductGateway;
import com.fiap.tech.product.domain.ProductID;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultCreateOrderUseCase extends CreateOrderUseCase {

    private final OrderGateway orderGateway;
    private final ClientGateway clientGateway;
    private final ProductGateway productGateway;
    private final OrderedItemGateway orderedItemGateway;

    public DefaultCreateOrderUseCase(
            final OrderGateway orderGateway,
            final ClientGateway clientGateway,
            final ProductGateway productGateway,
            final OrderedItemGateway orderedItemGateway
    ) {
        this.orderGateway = orderGateway;
        this.clientGateway = clientGateway;
        this.productGateway = productGateway;
        this.orderedItemGateway = orderedItemGateway;
    }

    @Override
    public CreateOrderOutput execute(CreateOrderCommand command) {
        final var client = command.clientID();
        final var items = command.items();

        final var notification = Notification.create();
        notification.append(validateClient(client));
        notification.append(validateItems(items));

        ClientID clientId = client != null ? ClientID.from(client) : null;
        final var order = Order.newOrder(BigDecimal.ZERO, null, clientId);

        if (notification.hasErrors()) {
            throw NotificationException.with(notification.getErrors());
        }

        List<Product> productsList = productGateway.findByIds(items.stream().map(ItemCommand::productID).toList());

        List<OrderedItem> orderedItems = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            final var item = items.get(i);
            final var product = productsList.get(i);
            final var orderedItem = OrderedItem.newOrderedItem(product.getId(), item.quantity(), product.getPrice());
            orderedItemGateway.create(orderedItem);
            orderedItems.add(orderedItem);
        }

        order.setOrderedItems(orderedItems.stream().map(OrderedItem::getId).toList());
        order.setTotal(BigDecimal.valueOf(orderedItems.stream().mapToDouble(orderedItem -> orderedItem.getSubTotal().doubleValue()).sum()));

        order.registerEvent(new OrderCreated(
                order.getId().getValue(),
                order.getClientId() != null ? order.getClientId().getValue() : null,
                order.getTotal()
        ));

        return CreateOrderOutput.from(this.orderGateway.create(order));
    }

    private ValidationHandler validateClient(String client) {
        if (!StringUtils.hasText(client)) {
            return Notification.create();
        }
        final var existClient = clientGateway.existsByID(client);

        if (!existClient) {
            return Notification.create(new Error("Client with ID = %s not found.".formatted(client)));
        }

        return Notification.create();
    }

    private ValidationHandler validateItems(List<ItemCommand> itemCommands) {
        if (itemCommands == null || itemCommands.isEmpty()) {
            return Notification.create(new Error("Order must have at least one product."));
        }
        final var ids = itemCommands.stream().map(ItemCommand::productID).toList();

        final var retrievedIds = productGateway.existsByIds(ids);

        if (ids.size() != retrievedIds.size()) {
            final var missingIds = new ArrayList<>(ids);
            missingIds.removeAll(retrievedIds.stream().map(ProductID::getValue).toList());

            final var missingIdsMessage = String.join(", ", missingIds);

            return Notification.create(new Error("Some items couldn't be found: %s".formatted(missingIdsMessage)));
        }
        return Notification.create();
    }
}
