package com.fiap.tech.order.application.update;

public record UpdateOrderCommand(String id, String status) {

    public static UpdateOrderCommand with(final String id, final String status) {
        return new UpdateOrderCommand(id, status);
    }
}
