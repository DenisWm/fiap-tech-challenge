package com.fiap.tech.order.application.update;
import com.fiap.tech.order.domain.OrderGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class OrderUseCaseConfig {
    private final OrderGateway orderGateway;

    public OrderUseCaseConfig(final OrderGateway orderGateway){
        this.orderGateway = Objects.requireNonNull(orderGateway);
    }

    @Bean
    public DefaultUpdateOrderUseCase updateOrderStatusUseCase(){
        return new DefaultUpdateOrderUseCase(orderGateway);

    }

}
