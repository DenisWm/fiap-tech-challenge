package com.fiap.tech.order.infra.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.tech.order.application.retrieve.get.OrderOutput;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponse(
        @JsonProperty("id") String id,
        @JsonProperty("timestamp") Instant timestamp,
        @JsonProperty("client") OrderClientResponse client,
        @JsonProperty("total") BigDecimal total,
        @JsonProperty("status") String status,
        @JsonProperty("items") List<OrderItemResponse> items,
        @JsonProperty("payment_id") String paymentId
) {

    public static OrderResponse from(final OrderOutput output) {
        return new OrderResponse(
                output.getId(),
                output.getTimestamp(),
                output.getClient() != null ? OrderClientResponse.from(output.getClient()) : null,
                output.getTotal(),
                output.getStatus() != null ? output.getStatus().getValue() : null,
                output.getOrderedItems().stream().map(OrderItemResponse::from).toList(),
                output.getPaymentId()
        );
    }

    public record OrderItemResponse(
            String id, Integer quantity, BigDecimal subTotal, OrderedItemProductResponse product
    ) {

        public static OrderItemResponse from(final OrderOutput.OrderedItemOutput output) {
            return new OrderItemResponse(
                    output.id(),
                    output.quantity(),
                    output.subTotal(),
                    OrderItemResponse.OrderedItemProductResponse.from(output.product())
            );
        }

        public record OrderedItemProductResponse(
                String id,
                String name,
                BigDecimal price
        ) {
            public static OrderedItemProductResponse from(final OrderOutput.OrderedItemOutput.OrderedItemProductOutput output) {
                return new OrderedItemProductResponse(
                        output.id(),
                        output.name(),
                        output.price()
                );
            }
        }
    }
}
