package com.fiap.tech.configuration;

import com.fiap.tech.configuration.amqp.QueueProperties;
import com.fiap.tech.configuration.annotations.OrderCreatedQueue;
import com.fiap.tech.services.EventService;
import com.fiap.tech.services.impl.RabbitEventService;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfig {

    @Bean
    @OrderCreatedQueue
    EventService orderCreatedEventService(
            @OrderCreatedQueue final QueueProperties props,
            final RabbitOperations ops
            ){
        return new RabbitEventService(props.getExchange(), props.getRoutingKey(), ops);
    }
}
