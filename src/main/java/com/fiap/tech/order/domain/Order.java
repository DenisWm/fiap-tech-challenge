package com.fiap.tech.order.domain;

import com.fiap.tech.common.domain.AggregateRoot;
import com.fiap.tech.common.domain.validation.ValidationHandler;
import com.fiap.tech.client.domain.ClientID;
import com.fiap.tech.product.domain.ProductID;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class Order extends AggregateRoot<OrderID> {

    private Instant timestamp;

    private ClientID clientId;

    private List<ProductID> products;

    private BigDecimal total;

    private OrderStatus status;

    private Order(OrderID orderID, Instant timestamp, BigDecimal total, List<ProductID> products, ClientID clientId, OrderStatus status) {
        super(orderID);
        this.timestamp = timestamp;
        this.total = total;
        this.products = products;
        this.clientId = clientId;
        this.status = status;
    }

    public static Order newOrder(ClientID clientID, BigDecimal total, List<ProductID> products){
        final var now = Instant.now();
        final var orderID = OrderID.unique();
        final var status = OrderStatus.RECEIVED;

        return new Order(orderID,now,total,products, clientID, status);
    }

    public static Order with(OrderID orderID, Instant timestamp, BigDecimal total, List<ProductID> products, ClientID client,OrderStatus status){
        return new Order(orderID, timestamp, total, products,client, status);
    }

    public static Order with(Order order){
        return with(order.getId(), order.getTimestamp(), order.getTotal(), order.getProducts(), order.getClientId(), order.getStatus());
    }

    @Override
    public void validate(ValidationHandler handler) {

    }

}
