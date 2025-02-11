package com.fiap.tech.order.domain.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.tech.common.domain.events.DomainEvent;

import java.math.BigDecimal;
import java.time.Instant;

public record OrderCreated (
         String orderID,
         String clientID,
         BigDecimal amount,
         Instant occurredOn

) implements DomainEvent {

    public OrderCreated(final String orderID, final String clientID, final BigDecimal amount) {
        this(orderID, clientID, amount, Instant.now());
    }
}
