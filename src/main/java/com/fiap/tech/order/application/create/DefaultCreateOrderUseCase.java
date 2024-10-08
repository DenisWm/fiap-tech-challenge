package com.fiap.tech.order.application.create;

import com.fiap.tech.common.domain.exceptions.NotificationException;
import com.fiap.tech.common.domain.validation.Error;
import com.fiap.tech.common.domain.validation.ValidationHandler;
import com.fiap.tech.common.domain.validation.handler.Notification;
import com.fiap.tech.client.domain.ClientGateway;
import com.fiap.tech.client.domain.ClientID;
import com.fiap.tech.order.domain.Order;
import com.fiap.tech.order.domain.OrderGateway;
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

    public DefaultCreateOrderUseCase(OrderGateway orderGateway, ClientGateway clientGateway, ProductGateway productGateway) {
        this.orderGateway = orderGateway;
        this.clientGateway = clientGateway;
        this.productGateway = productGateway;
    }

    @Override
    public CreateOrderOutput execute(CreateOrderCommand command) {
        final var client = command.clientID();
        final var products = command.products();

        final var notification = Notification.create();
        notification.append(validateClient(client));
        notification.append(validateProducts(products));

        if(notification.hasErrors()){
            throw NotificationException.with(notification.getErrors());
        }
        final var order = Order.newOrder(client != null ? ClientID.from(client) : null, BigDecimal.ZERO, products.stream().map(
                ProductID::from).toList());

        List<Product> productsList = productGateway.findByIds(products);

        final var total = productsList.stream().mapToDouble(product -> product.getPrice().doubleValue()).sum();

        order.setTotal(BigDecimal.valueOf(total));

        return CreateOrderOutput.from(this.orderGateway.create(order));
    }

    private ValidationHandler validateClient(String client) {
        if(!StringUtils.hasText(client)){
           return Notification.create();
        }
        final var existClient = clientGateway.existsByID(client);

        if(!existClient) {
            return Notification.create(new Error("Client with ID = %s not found.".formatted(client)));
        }

        return Notification.create();
    }

    private  ValidationHandler validateProducts(List<String> products){
        if(products == null || products.isEmpty()){
            return Notification.create(new Error("Order must have at least one product."));
        }
        final var existsIDs = productGateway.existsByIds(products);

        if(products.size() != existsIDs.size()){
            final var missingIds = new ArrayList<>(products);
            missingIds.removeAll(existsIDs);

            return Notification.create(new Error("Some products couldn't be found: %s".formatted(missingIds.stream().collect(Collectors.joining(", ")))));
        }
        return Notification.create();
    }
}
