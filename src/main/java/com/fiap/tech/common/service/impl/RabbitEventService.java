package com.fiap.tech.common.service.impl;


import com.fiap.tech.common.infra.configuration.Json;
import com.fiap.tech.common.service.EventService;
import org.springframework.amqp.rabbit.core.RabbitOperations;

import java.util.Objects;

public class RabbitEventService implements EventService {

    private final String exchange;
    private final String routingKey;
    private final RabbitOperations ops;

    public RabbitEventService(final String exchange, final String routingKey, final RabbitOperations ops) {
        this.exchange = Objects.requireNonNull(exchange);
        this.routingKey = Objects.requireNonNull(routingKey);
        this.ops = Objects.requireNonNull(ops);
    }

    @Override
    public void send(final Object event) {
        ops.convertAndSend(this.exchange, this.routingKey, Json.writeValueAsString(event));
    }
}
