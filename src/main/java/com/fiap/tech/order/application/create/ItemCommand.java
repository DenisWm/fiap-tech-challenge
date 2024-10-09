package com.fiap.tech.order.application.create;

public record ItemCommand(
        String productID,
        Integer quantity) {

    public static ItemCommand with(String productID, Integer quantity) {
        return new ItemCommand(productID, quantity);
    }
}
