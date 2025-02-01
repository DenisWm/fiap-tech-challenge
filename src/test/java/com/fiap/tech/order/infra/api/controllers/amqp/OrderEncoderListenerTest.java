package com.fiap.tech.order.infra.api.controllers.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.tech.AmqpTest;
import com.fiap.tech.amqp.OrderEncodedListener;
import com.fiap.tech.configuration.amqp.QueueProperties;
import com.fiap.tech.configuration.annotations.OrderEncodedQueue;
import com.fiap.tech.order.application.update.UpdateOrderStatusUseCase;
import com.fiap.tech.order.infra.models.OrderEncoderCompleted;
import com.fiap.tech.order.infra.models.OrderEncoderErro;
import com.fiap.tech.order.infra.models.OrderMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness;
import org.springframework.amqp.rabbit.test.TestRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.concurrent.TimeUnit;

@AmqpTest
class OrderEncoderListenerTest {

    @Autowired
    private TestRabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitListenerTestHarness harness;

    @MockBean
    private UpdateOrderStatusUseCase updateOrderStatusUseCase;

    @Autowired
    @OrderEncodedQueue
    private QueueProperties queueProperties;

    @Autowired
    private ObjectMapper objectMapper; // Jackson para serialização

    @Test
    public void givenErrorResult_whenCallsListener_shouldProcess() throws Exception {
        // Criando um objeto OrderEncoderErro
        final var expectedError = new OrderEncoderErro(
                new OrderMessage("123", "abc"),
                "Order Not Found"
        );

        final var expectedMessage = objectMapper.writeValueAsString(expectedError);

        this.rabbitTemplate.convertAndSend(queueProperties.getRoutingKey(), expectedMessage);

        final var invocationData = harness.getNextInvocationDataFor(
                OrderEncodedListener.LISTENER_ID, 2, TimeUnit.SECONDS);

        Assertions.assertNotNull(invocationData);
        Assertions.assertNotNull(invocationData.getArguments());

        final var actualMessage = (String) invocationData.getArguments()[0];

        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
