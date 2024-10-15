package com.fiap.tech.order.infra.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CreateOrderRequest (
        @JsonProperty("client_id") String clientId,
        @JsonProperty("items") List<ItemsRequest> items
) {

}
