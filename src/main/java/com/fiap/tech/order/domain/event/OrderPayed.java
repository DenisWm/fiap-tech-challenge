package com.fiap.tech.order.domain.event;

import com.fiap.tech.common.domain.events.DomainEvent;

import java.time.Instant;
import java.util.List;

public record OrderPayed(
         String orderID,
         List<Item> items,
         Instant occurredOn

) implements DomainEvent {

    public OrderPayed(final String orderID, List<Item> items) {
        this(orderID, items, Instant.now());
    }

    public record Item(String productId, String productName, int quantity){

        public static Item with(final String productId,
                                final String productName,
                                final int quantity) {
            return new Item(productId, productName, quantity);
        }
    }
}
