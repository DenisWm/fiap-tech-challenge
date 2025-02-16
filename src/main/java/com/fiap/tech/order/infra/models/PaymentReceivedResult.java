package com.fiap.tech.order.infra.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentReceivedResult(
        @JsonProperty("payment_id") String paymentID,
        @JsonProperty("order_id") String orderID,
        @JsonProperty("client_id") String clientID,
        BigDecimal amount,
        @JsonProperty("created_at") Instant createdAt,
        @JsonProperty("payment_status") String paymentStatus,
        @JsonProperty("occurred_on") Instant occurredOn
) {
}
