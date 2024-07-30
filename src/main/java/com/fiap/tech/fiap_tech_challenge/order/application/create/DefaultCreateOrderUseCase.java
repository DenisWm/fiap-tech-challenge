package com.fiap.tech.fiap_tech_challenge.order.application.create;

import com.fiap.tech.fiap_tech_challenge.client.domain.ClientGateway;
import com.fiap.tech.fiap_tech_challenge.client.domain.ClientID;
import com.fiap.tech.fiap_tech_challenge.common.domain.exceptions.NotificationException;
import com.fiap.tech.fiap_tech_challenge.common.domain.validation.Error;
import com.fiap.tech.fiap_tech_challenge.common.domain.validation.ValidationHandler;
import com.fiap.tech.fiap_tech_challenge.common.domain.validation.handler.Notification;
import com.fiap.tech.fiap_tech_challenge.order.domain.Order;
import com.fiap.tech.fiap_tech_challenge.order.domain.OrderGateway;
import com.fiap.tech.fiap_tech_challenge.product.domain.Product;
import com.fiap.tech.fiap_tech_challenge.product.domain.ProductGateway;
import com.fiap.tech.fiap_tech_challenge.product.domain.ProductID;
import com.fiap.tech.fiap_tech_challenge.product.infra.persistense.ProductJpaEntity;

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
        final var order = Order.newOrder(ClientID.from(client), BigDecimal.ZERO, products.stream().map(ProductID::from).toList());

        List<Product> productsList = productGateway.findByIds(products);

        final var total = productsList.stream().mapToDouble(product -> product.getPrice().doubleValue()).sum();

        order.setTotal(BigDecimal.valueOf(total));

        return CreateOrderOutput.from(this.orderGateway.create(order));
    }

    private ValidationHandler validateClient(String client) {
        if(client == null){
           return Notification.create(new Error("Client ID can't be null."));
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
