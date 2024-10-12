package com.fiap.tech.order.application.update;


import com.fiap.tech.order.domain.Order;
import com.fiap.tech.product.domain.Product;

public record UpdateOrderOutput(
        String id
) {
    public static UpdateOrderOutput from (final Order aOrder){
        return new UpdateOrderOutput(aOrder.getId().getValue());
    }

    public static UpdateOrderOutput from (final String anId){
        return new UpdateOrderOutput(anId);
    }
}
