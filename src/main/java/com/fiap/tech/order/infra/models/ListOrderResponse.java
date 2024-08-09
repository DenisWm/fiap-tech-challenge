package com.fiap.tech.order.infra.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.tech.order.application.retrieve.list.OrderListOutput;

import java.math.BigDecimal;
import java.time.Instant;

public record ListOrderResponse(
        @JsonProperty("id") String id,
        @JsonProperty("timestamp") Instant timestamp,
        @JsonProperty("client_id") String client,
        @JsonProperty("total") BigDecimal total,
        @JsonProperty("status") String status
) {

    public static ListOrderResponse from(final OrderListOutput output) {
        return new ListOrderResponse(
                output.id(),
                output.timestamp(),
                output.client(),
                output.total(),
                output.status()
        );
    }
}
