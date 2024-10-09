package com.fiap.tech.order.application.create;

import java.util.List;

public record CreateOrderCommand(
        String clientID,
        List<ItemCommand> items
) {

    public static CreateOrderCommand with(String clientID, List<ItemCommand> items){
        return new CreateOrderCommand(clientID,items);
    }

}
