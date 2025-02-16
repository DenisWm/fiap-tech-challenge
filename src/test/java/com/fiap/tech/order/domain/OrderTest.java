package com.fiap.tech.order.domain;

import com.fiap.tech.client.domain.ClientID;
import com.fiap.tech.ordereditens.domain.OrderedItemID;
import com.fiap.tech.payment.domain.PaymentID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private ClientID clientID;
    private List<OrderedItemID> orderedItems;
    private BigDecimal total;
    private Order order;

    @BeforeEach
    void setUp() {
        clientID = ClientID.from(UUID.randomUUID().toString());
        orderedItems = List.of(OrderedItemID.from(UUID.randomUUID().toString()));
        total = new BigDecimal("100.00");
        order = Order.newOrder(total, orderedItems, clientID);
    }

    @Test
    void shouldCreateNewOrder() {
        assertThat(order).isNotNull();
        assertThat(order.getId()).isNotNull();
        assertThat(order.getTimestamp()).isNotNull();
        assertThat(order.getOrderedItems()).isEqualTo(orderedItems);
        assertThat(order.getTotal()).isEqualByComparingTo(total);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.RECEIVED);
        assertThat(order.getClientId()).isEqualTo(clientID);
        assertThat(order.getPaymentId()).isNull();
    }

    @Test
    void shouldUpdateOrderStatus() {
        order.update(OrderStatus.IN_PREPARATION);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.IN_PREPARATION);
    }

    @Test
    void shouldCreateOrderWithExistingOrder() {
        Order copiedOrder = Order.with(order);
        assertThat(copiedOrder).isNotNull();
        assertThat(copiedOrder.getId()).isEqualTo(order.getId());
        assertThat(copiedOrder.getTimestamp()).isEqualTo(order.getTimestamp());
        assertThat(copiedOrder.getOrderedItems()).isEqualTo(order.getOrderedItems());
        assertThat(copiedOrder.getTotal()).isEqualTo(order.getTotal());
        assertThat(copiedOrder.getStatus()).isEqualTo(order.getStatus());
        assertThat(copiedOrder.getClientId()).isEqualTo(order.getClientId());
        assertThat(copiedOrder.getPaymentId()).isEqualTo(order.getPaymentId());
    }

    @Test
    void shouldSetPaymentId() {
        PaymentID paymentID = PaymentID.unique();
        order.setPaymentId(paymentID);
        assertThat(order.getPaymentId()).isEqualTo(paymentID);
    }
}