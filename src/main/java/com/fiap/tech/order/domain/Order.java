package com.fiap.tech.order.domain;

import com.fiap.tech.common.domain.AggregateRoot;
import com.fiap.tech.common.domain.validation.ValidationHandler;
import com.fiap.tech.client.domain.ClientID;
import com.fiap.tech.event.DomainEvent;
import com.fiap.tech.ordereditens.domain.OrderedItemID;
import com.fiap.tech.payment.domain.Payment;
import com.fiap.tech.payment.domain.PaymentID;
import com.fiap.tech.payment.domain.PaymentStatus;
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

    private List<OrderedItemID> orderedItems;

    private BigDecimal total;

    private OrderStatus status;

    private ClientID clientId;

    private PaymentID paymentId;

    public Order(OrderID orderID, Instant timestamp, List<OrderedItemID> orderedItems, BigDecimal total, OrderStatus status, ClientID clientId, PaymentID paymentId, final List<DomainEvent> domainEvents) {
        super(orderID, domainEvents);
        this.timestamp = timestamp;
        this.orderedItems = orderedItems;
        this.total = total;
        this.status = status;
        this.clientId = clientId;
        this.paymentId = paymentId;
    }



    public static Order newOrder(BigDecimal total, List<OrderedItemID> orderedItems, ClientID clientID) {
        final var now = Instant.now();
        final var orderID = OrderID.unique();
        final var status = OrderStatus.RECEIVED;
        final var paymentID = PaymentID.unique();

        return new Order(orderID, now, orderedItems != null ? orderedItems : new ArrayList<>(), total, status, clientID, paymentID, null);
    }

    public Order update(final OrderStatus status) {
        this.status = status;
        return this;
    }


    public static Order with(OrderID orderID, Instant timestamp, List<OrderedItemID> orderedItems, BigDecimal total, OrderStatus status, ClientID clientId, PaymentID paymentID){
        return new Order(orderID, timestamp, orderedItems, total, status, clientId, paymentID, null);
    }

    public static Order with(Order order){
        return with(order.getId(), order.getTimestamp(), order.getOrderedItems(), order.getTotal(), order.getStatus(), order.getClientId(), order.getPaymentId());
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
