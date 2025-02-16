package com.fiap.tech.common.service.impl;

import com.fiap.tech.AmqpTest;
import com.fiap.tech.common.infra.annotations.OrderCreatedQueue;
import com.fiap.tech.common.infra.annotations.OrderPayedQueue;
import com.fiap.tech.common.infra.configuration.Json;
import com.fiap.tech.common.service.EventService;
import com.fiap.tech.order.domain.event.OrderCreated;
import com.fiap.tech.order.domain.event.OrderPayed;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@AmqpTest
class RabbitEventServiceTest {
    private static final String ORDER_CREATED_LISTENER = "order.created";
    private static final String ORDER_PAYED_LISTENER = "order.payed";

    @Autowired
    @OrderCreatedQueue
    private EventService orderCreatedPublisher;

    @Autowired
    @OrderPayedQueue
    private EventService orderPayedPublisher;

    @Autowired
    private RabbitListenerTestHarness harness;

    @Test
    public void shouldSendOrderCreatedMessage() throws InterruptedException {
        final var notification = new OrderCreated("orderId", "clientId", BigDecimal.TEN);

        final var expectedMessage = Json.writeValueAsString(notification);

        this.orderCreatedPublisher.send(notification);

        final var invocationData = harness.getNextInvocationDataFor(ORDER_CREATED_LISTENER, 1, TimeUnit.SECONDS);

        assertNotNull(invocationData);
        assertNotNull(invocationData.getArguments());

        final var actualMessage = (String) invocationData.getArguments()[0];

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void shouldSendOrderPayedMessage() throws InterruptedException {
        final var notification =
                new OrderPayed("orderId", List.of(OrderPayed.Item.with("productId", "productName", 5)));

        final var expectedMessage = Json.writeValueAsString(notification);

        this.orderPayedPublisher.send(notification);

        final var invocationData = harness.getNextInvocationDataFor(ORDER_PAYED_LISTENER, 1, TimeUnit.SECONDS);

        assertNotNull(invocationData);
        assertNotNull(invocationData.getArguments());

        final var actualMessage = (String) invocationData.getArguments()[0];

        assertEquals(expectedMessage, actualMessage);
    }

    @Configuration
    static class NewsListener {

        @RabbitListener(id = ORDER_CREATED_LISTENER, queues = "${amqp.queues.order-created.routing-key}")
        void onOrderCreated(@Payload String message) {

        }

        @RabbitListener(id = ORDER_PAYED_LISTENER, queues = "${amqp.queues.order-payed.routing-key}")
        void onOrderPayed(@Payload String message) {

        }
    }
}