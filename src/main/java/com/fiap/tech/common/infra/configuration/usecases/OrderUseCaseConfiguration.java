package com.fiap.tech.common.infra.configuration.usecases;

import com.fiap.tech.client.domain.ClientGateway;
import com.fiap.tech.order.application.create.CreateOrderUseCase;
import com.fiap.tech.order.application.create.DefaultCreateOrderUseCase;
import com.fiap.tech.order.application.retrieve.get.DefaultGetOrderByIdUseCase;
import com.fiap.tech.order.application.retrieve.get.GetOrderByIdUseCase;
import com.fiap.tech.order.application.retrieve.list.DefaultListOrderUseCase;
import com.fiap.tech.order.application.retrieve.list.ListOrderUseCase;
import com.fiap.tech.order.domain.OrderGateway;
import com.fiap.tech.ordereditens.domain.OrderedItemGateway;
import com.fiap.tech.product.domain.ProductGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class OrderUseCaseConfiguration {

    private final OrderGateway orderGateway;
    private final ClientGateway clientGateway;
    private final ProductGateway productGateway;
    private final OrderedItemGateway orderedItemGateway;

    public OrderUseCaseConfiguration(final OrderGateway orderGateway, final ClientGateway clientGateway, final ProductGateway productGateway, OrderedItemGateway orderedItemGateway) {
        this.orderGateway = Objects.requireNonNull(orderGateway);
        this.clientGateway = Objects.requireNonNull(clientGateway);
        this.productGateway = Objects.requireNonNull(productGateway);
        this.orderedItemGateway = orderedItemGateway;
    }

    @Bean
    public CreateOrderUseCase createOrderUseCase() {
        return new DefaultCreateOrderUseCase(orderGateway, clientGateway, productGateway, orderedItemGateway);
    }

    @Bean
    public GetOrderByIdUseCase getOrderByIdUseCase() {
        return new DefaultGetOrderByIdUseCase(productGateway, orderedItemGateway, orderGateway);
    }

    @Bean
    public ListOrderUseCase listOrderUseCase() {
        return new DefaultListOrderUseCase(orderGateway);
    }
}
