package com.fiap.tech.services.impl;

import com.fiap.tech.services.EventService;
import jakarta.json.Json;
import org.springframework.amqp.rabbit.core.RabbitOperations;

import java.util.Objects;

public class RabbitEventService implements EventService {

    private final String exchange;
    private final String routingKey;
    private final RabbitOperations ops;

    public RabbitEventService(
            final String exchange,
            final String routingKey,
            final RabbitOperations ops)
    {
        this.exchange = Objects.requireNonNull(exchange);
        this.routingKey = Objects.requireNonNull(routingKey);
        this.ops = Objects.requireNonNull(ops);
    }

    @Override
    public void send(Object event) {

    }

    //Revisar
    /*
    @Override
    public void send(Object event){
        this.ops.convertAndSend(this.exchange, this.routingKey, Json.writeValueAsString(event));
    }*/
}
