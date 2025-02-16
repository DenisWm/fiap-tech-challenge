package com.fiap.tech.payment.infra.persistence;

import com.fiap.tech.IntegrationTest;
import com.fiap.tech.payment.domain.Payment;
import com.fiap.tech.payment.domain.PaymentID;
import com.fiap.tech.payment.domain.PaymentStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
@IntegrationTest
class PaymentPostgresGatewayTest {

    @Autowired
    private PaymentPostgresGateway paymentGateway;

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    void testInjection() {
        assertNotNull(paymentGateway);
        assertNotNull(paymentRepository);
    }

    @Test
    void givenPayment_whenCallsCreate_shouldReturnPayment() {
        final var expectedPaymentId = PaymentID.unique();
        final var expectedAmount = BigDecimal.TEN;
        final var expectedTimestamp = Instant.now();
        final var expectedStatus = PaymentStatus.APPROVED;

        final var payment = Payment.receivePayment(expectedPaymentId, expectedAmount, expectedTimestamp, expectedStatus);

        assertEquals(0, paymentRepository.count());

        final var aResult = paymentGateway.create(payment);

        assertEquals(1, paymentRepository.count());

        assertEquals(expectedPaymentId, aResult.getId());
        assertEquals(expectedAmount, aResult.getAmount());
        assertEquals(expectedTimestamp, aResult.getTimestamp());
        assertEquals(expectedStatus, aResult.getStatus());
    }
}