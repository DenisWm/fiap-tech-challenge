package com.fiap.tech.order.infra.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.tech.order.application.retrieve.get.OrderOutput;


import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponse(
        @JsonProperty("id") String id,
        @JsonProperty("timestamp") Instant timestamp,
        @JsonProperty("client_id") String clientId,
        @JsonProperty("total") BigDecimal total,
        @JsonProperty("status") String status,
        @JsonProperty("products") List<OrderProductResponse> products
) {

    public static OrderResponse from(final OrderOutput output) {
        return new OrderResponse(
                output.getId(),
                output.getTimestamp(),
                output.getClientId(),
                output.getTotal(),
                output.getStatus() != null ? output.getStatus().getValue() : null,
                output.getProducts().stream().map(OrderProductResponse::from).toList()
        );
    }

    public record OrderProductResponse(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("price") BigDecimal price
    ) {

        public static OrderProductResponse from(final OrderOutput.OrderProductOutput output) {
            return new OrderProductResponse(
                    output.id(),
                    output.name(),
                    output.price()
            );
        }
    }
}
