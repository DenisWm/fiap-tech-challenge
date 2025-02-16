package com.fiap.tech.order.infra.models;

import com.fiap.tech.JacksonTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@JacksonTest
class PaymentReceivedResultTest {

    @Autowired
    private JacksonTester<PaymentReceivedResult> json;

    @Test
    public void testUnmarshall() throws Exception {
        final var expectedPaymentId = "123";
        final var expectedOrderId = "123";
        final var expectedClientId = "123";
        final var expectedAmount = BigDecimal.TEN;
        final var expectedCreatedAt = Instant.now();
        final var expectedPaymentStatus = "COMPLETED";
        final var expectedOccurredOn = Instant.now();

        final var json = """
                {
                  "payment_id": "%s",
                  "order_id": "%s",
                  "client_id": "%s",
                  "amount": "%s",
                  "created_at": "%s",
                  "payment_status": "%s",
                  "occurred_on": "%s"
                }  
                """.formatted(expectedPaymentId,
                expectedOrderId,
                expectedClientId,
                expectedAmount,
                expectedCreatedAt,
                expectedPaymentStatus,
                expectedOccurredOn );

        final var actualJson = this.json.parse(json);

        Assertions.assertThat(actualJson)
                .hasFieldOrPropertyWithValue("paymentID", expectedPaymentId)
                .hasFieldOrPropertyWithValue("orderID", expectedOrderId)
                .hasFieldOrPropertyWithValue("clientID", expectedClientId)
                .hasFieldOrPropertyWithValue("amount", expectedAmount)
                .hasFieldOrPropertyWithValue("createdAt", expectedCreatedAt)
                .hasFieldOrPropertyWithValue("paymentStatus", expectedPaymentStatus)
                .hasFieldOrPropertyWithValue("occurredOn", expectedOccurredOn)
        ;
    }

}