package com.fiap.tech.fiap_tech_challenge.order.application.create;

import java.util.List;

public record CreateOrderCommand(
        String clientID,
        List<String> products
) {

    public static CreateOrderCommand with(String clientID, List<String> products){
        return new CreateOrderCommand(clientID,products);
    }
}
