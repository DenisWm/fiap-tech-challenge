package com.fiap.tech.fiap_tech_challenge.order.infra.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.tech.fiap_tech_challenge.client.infra.models.ClientResponse;
import com.fiap.tech.fiap_tech_challenge.order.application.retrieve.list.OrderListOutput;

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
