package com.fiap.tech.fiap_tech_challenge.order.application.create;


import com.fiap.tech.fiap_tech_challenge.order.domain.Order;

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
