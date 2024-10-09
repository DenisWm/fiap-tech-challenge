package com.fiap.tech.order.infra.models;

public record ItemsRequest(
        String productId,
        Integer quantity
) {

    public static ItemsRequest from(String productId, Integer quantity) {
        return new ItemsRequest(productId, quantity);
    }
}
