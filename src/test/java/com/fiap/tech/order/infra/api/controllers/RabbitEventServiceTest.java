package com.fiap.tech.order.infra.api.controllers;

import com.fiap.tech.AmqpTest;
import com.fiap.tech.configuration.annotations.OrderCreatedQueue;
import com.fiap.tech.order.domain.Order;
import com.fiap.tech.services.EventService;
import io.swagger.v3.core.util.Json;
import org.junit.jupiter.api.Assertions;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.test.RabbitListenerTest;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@AmqpTest
public class RabbitEventServiceTest {

    private static final String LISTENER = "order.created";

    @Autowired
    @OrderCreatedQueue
    private EventService publisher;

    @Autowired
    private RabbitListenerTestHarness harness;

    /* Teste contém erros
    public void shouldSendMessage() throws InterruptedException {
        //given
        final var notification = new Order("resource", "filepath");
        final var expectedMessage = Json.writeValueAsString(notification);

        //when
        this.publisher.send(notification);

        //then
        final var invocationData = harness.getNextInvocationDataFor(LISTENER, 1, TimeUnit.SECONDS);

        Assertions.assertNotNull(invocationData.getArguments());
        Assertions.assertNotNull(invocationData);

        final var actualMessage = (String) invocationData.getArguments()[0];

        Assertions.assertEquals(expectedMessage, actualMessage);
    }*/

    @Component
    static class OrderCreatedNewsListener{

        @RabbitListener(id = LISTENER, queues = "${amqp.queues.order-created.routing-key}")
        void onOrderCreated(@Payload String message){

        }
    }
}
