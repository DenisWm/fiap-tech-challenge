package com.fiap.tech.order.infra.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record ProductionStatusChangedResult(
        @JsonProperty("order_id") String orderId,
        @JsonProperty("status") String status,
        @JsonProperty("started_at") Instant startedAt,
        @JsonProperty("finished_at") Instant finishedAt
) {
}
