package com.fiap.tech.common.infra.configuration;


import com.fiap.tech.common.infra.annotations.OrderCreatedQueue;
import com.fiap.tech.common.infra.annotations.OrderPayedQueue;
import com.fiap.tech.common.infra.configuration.amqp.QueueProperties;
import com.fiap.tech.common.service.EventService;
import com.fiap.tech.common.service.impl.RabbitEventService;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfig {

    @Bean
    @OrderCreatedQueue
    public EventService orderCreatedService(
            @OrderCreatedQueue final QueueProperties props,
            final RabbitOperations ops
    ) {
        return new RabbitEventService(props.getExchange(), props.getRoutingKey(), ops);
    }

    @Bean
    @OrderPayedQueue
    public EventService orderPayedEventService(
            @OrderPayedQueue final QueueProperties props,
            final RabbitOperations ops
    ) {
        return new RabbitEventService(props.getExchange(), props.getRoutingKey(), ops);
    }

}
