package com.fiap.tech.order.infra.amqp;

import com.fiap.tech.AmqpTest;
import com.fiap.tech.client.domain.ClientID;
import com.fiap.tech.common.infra.annotations.PaymentReceivedQueue;
import com.fiap.tech.common.infra.annotations.ProductionStatusChangedQueue;
import com.fiap.tech.common.infra.configuration.Json;
import com.fiap.tech.common.infra.configuration.amqp.QueueProperties;
import com.fiap.tech.order.domain.Order;
import com.fiap.tech.order.domain.OrderGateway;
import com.fiap.tech.order.infra.models.PaymentReceivedResult;
import com.fiap.tech.ordereditens.domain.OrderedItemID;
import com.fiap.tech.payment.application.receive.ReceivePaymentCommand;
import com.fiap.tech.payment.application.receive.ReceivePaymentOutput;
import com.fiap.tech.payment.application.receive.ReceivePaymentUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness;
import org.springframework.amqp.rabbit.test.TestRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@AmqpTest
class PaymentReceivedListenerTest {

    @Autowired
    private TestRabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitListenerTestHarness harness;

    @MockBean
    private ReceivePaymentUseCase receivePaymentUseCase;

    @Autowired
    private OrderGateway orderGateway;

    @Autowired
    @PaymentReceivedQueue
    private QueueProperties queueProperties;

    @Test
    public void GivenPaymentReceivedResult_whenCallsListener_shouldProcess() throws InterruptedException {
        final var expectedOrderId = "123";
        final var expectedPaymentId = "123";
        final var expectedClientId = "123";
        final var expectedAmount = BigDecimal.TEN;
        final var expectedCreatedAt = Instant.now();
        final var expectedOccurredOn = Instant.now();
        final var expectedPaymentStatus = "Approved";

        final var expectedResult = new PaymentReceivedResult(
                expectedPaymentId,
                expectedOrderId,
                expectedClientId,
                expectedAmount,
                expectedCreatedAt,
                expectedPaymentStatus,
                expectedOccurredOn
        );

        final var expectedMessage = Json.writeValueAsString(expectedResult);

        when(receivePaymentUseCase.execute(any())).thenReturn(new ReceivePaymentOutput(expectedPaymentId));

        this.rabbitTemplate.convertAndSend(queueProperties.getQueue(), expectedMessage);

        final var invocationData = harness.getNextInvocationDataFor(PaymentReceivedListener.LISTENER_ID, 1, TimeUnit.SECONDS);

        assertNotNull(invocationData);
        assertNotNull(invocationData.getArguments());

        final var actualMessage = (String) invocationData.getArguments()[0];

        assertEquals(expectedMessage, actualMessage);

        final var cmdCaptor = ArgumentCaptor.forClass(ReceivePaymentCommand.class);

        verify(receivePaymentUseCase).execute(cmdCaptor.capture());

        final var actualCommand = cmdCaptor.getValue();

        assertEquals(expectedPaymentId, actualCommand.paymentID());
        assertEquals(expectedOrderId, actualCommand.orderID());
        assertEquals(expectedClientId, actualCommand.clientID());
        assertEquals(expectedAmount, actualCommand.amount());
        assertEquals(expectedCreatedAt, actualCommand.createdAt());
    }
}