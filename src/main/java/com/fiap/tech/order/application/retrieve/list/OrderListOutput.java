package com.fiap.tech.order.application.retrieve.list;

import com.fiap.tech.order.domain.Order;

import java.math.BigDecimal;
import java.time.Instant;

public record OrderListOutput(
        String id,
        Instant timestamp,
        String client,
        BigDecimal total,
        String status
) {

    public static OrderListOutput from(final Order anOrder) {
        return new OrderListOutput(
                anOrder.getId().getValue(),
                anOrder.getTimestamp(),
                anOrder.getClientId() != null ? anOrder.getClientId().getValue() : null,
                anOrder.getTotal(),
                anOrder.getStatus().getValue()
        );
    }
}
