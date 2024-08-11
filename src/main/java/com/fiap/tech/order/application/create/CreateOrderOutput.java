package com.fiap.tech.order.application.create;


import com.fiap.tech.order.domain.Order;

public record CreateOrderOutput(
        String id
) {
    public static CreateOrderOutput from (final Order order){
        return new CreateOrderOutput(order.getId().getValue());
    }

    public static CreateOrderOutput from (final String anId){
        return new CreateOrderOutput(anId);
    }
}
