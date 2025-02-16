package com.fiap.tech.order.infra.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ItemsRequest(
        @JsonProperty("product_id") String productId,
        @JsonProperty("quantity") Integer quantity
) {

    public static ItemsRequest from(String productId, Integer quantity) {
        return new ItemsRequest(productId, quantity);
    }
}
