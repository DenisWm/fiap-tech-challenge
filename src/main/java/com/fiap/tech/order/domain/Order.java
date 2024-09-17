package com.fiap.tech.order.domain;

import com.fiap.tech.common.domain.AggregateRoot;
import com.fiap.tech.common.domain.validation.ValidationHandler;
import com.fiap.tech.client.domain.ClientID;
import com.fiap.tech.ordereditens.domain.OrderedItemID;
import com.fiap.tech.payment.domain.PaymentID;
import com.fiap.tech.product.domain.ProductID;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Order extends AggregateRoot<OrderID> {

    private Instant timestamp;

    private ClientID clientId;

    private PaymentID paymentId;

    private List<OrderedItemID> orderedItems;

    private BigDecimal total;

    private OrderStatus status;

    private Order(OrderID orderID, Instant timestamp, BigDecimal total, List<OrderedItemID> orderedItems, ClientID clientId, OrderStatus status) {
        super(orderID);
        this.timestamp = timestamp;
        this.total = total;
        this.orderedItems = orderedItems;
        this.clientId = clientId;
        this.status = status;
    }

    public static Order newOrder(ClientID clientID, BigDecimal total, List<OrderedItemID> orderedItems){
        final var now = Instant.now();
        final var orderID = OrderID.unique();
        final var status = OrderStatus.RECEIVED;

        return new Order(orderID,now,total,orderedItems != null ? orderedItems : new ArrayList<>(), clientID, status);
    }


    public static Order with(OrderID orderID, Instant timestamp, BigDecimal total, List<OrderedItemID> orderedItems, ClientID client,OrderStatus status){
        return new Order(orderID, timestamp, total, orderedItems,client, status);
    }

    public static Order with(Order order){
        return with(order.getId(), order.getTimestamp(), order.getTotal(), order.getOrderedItems(), order.getClientId(), order.getStatus());
    }

    @Override
    public void validate(ValidationHandler handler) {

    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public ClientID getClientId() {
        return clientId;
    }

    public void setClientId(ClientID clientId) {
        this.clientId = clientId;
    }

    public PaymentID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(PaymentID paymentId) {
        this.paymentId = paymentId;
    }

    public List<OrderedItemID> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(List<OrderedItemID> orderedItems) {
        this.orderedItems = orderedItems;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
