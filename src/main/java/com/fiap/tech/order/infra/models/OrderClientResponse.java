package com.fiap.tech.order.infra.models;

import com.fiap.tech.order.application.retrieve.get.OrderOutput;

public record OrderClientResponse(
        String id,
        String name,
        String cpf
) {

    public static OrderClientResponse from(OrderOutput.OrderedItemOutput.OrderedItemClientOutput output) {
        return new OrderClientResponse(
                output.id(),
                output.name(),
                output.cpf()
        );
    }
}
