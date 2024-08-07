package com.fiap.tech.fiap_tech_challenge.order.application.retrieve.list;

import com.fiap.tech.fiap_tech_challenge.order.domain.Order;

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
                anOrder.getClientId().getValue(),
                anOrder.getTotal(),
                anOrder.getStatus().getValue()
        );
    }
}
