package com.fiap.tech.payment.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @Test
    void givenValidParams_whenCallsReceivePayment_thenShouldReturnPayment() {
        final var expectedId = PaymentID.unique();
        final var expectedAmount = BigDecimal.TEN;
        final var expectedStatus = PaymentStatus.APPROVED;
        final var expectedInstant = Instant.now();

        final var aPayment = Payment.receivePayment(expectedId, expectedAmount, expectedInstant, expectedStatus);

        assertNotNull(aPayment);
        assertEquals(expectedId, aPayment.getId());
        assertEquals(expectedAmount, aPayment.getAmount());
        assertEquals(expectedStatus, aPayment.getStatus());
        assertEquals(expectedInstant, aPayment.getTimestamp());
    }

    @Test
    void givenNoParams_whenCallsUnidentified_shouldReturnPayment() {
        final var aPayment = Payment.unidentified();

        assertNotNull(aPayment);
        assertEquals(BigDecimal.ZERO, aPayment.getAmount());
        assertEquals(PaymentStatus.UNIDENTIFIED, aPayment.getStatus());
    }

    @Test
    void givenValidParams_whenCallsWith_thenShouldReturnPayment() {
        final var expectedId = PaymentID.unique();
        final var expectedAmount = BigDecimal.TEN;
        final var expectedStatus = PaymentStatus.APPROVED;
        final var expectedInstant = Instant.now();

        final var aPayment = Payment.with(expectedId, expectedAmount, expectedInstant, expectedStatus);

        assertNotNull(aPayment);
        assertEquals(expectedId, aPayment.getId());
        assertEquals(expectedAmount, aPayment.getAmount());
        assertEquals(expectedStatus, aPayment.getStatus());
        assertEquals(expectedInstant, aPayment.getTimestamp());
    }

    @Test
    void givenValidParams_whenCallsWithPayment_thenShouldReturnPayment() {
        final var expectedId = PaymentID.unique();
        final var expectedAmount = BigDecimal.TEN;
        final var expectedStatus = PaymentStatus.APPROVED;
        final var expectedInstant = Instant.now();

        final var aPayment = Payment.with(Payment.receivePayment(expectedId, expectedAmount, expectedInstant, expectedStatus));

        assertNotNull(aPayment);
        assertEquals(expectedId, aPayment.getId());
        assertEquals(expectedAmount, aPayment.getAmount());
        assertEquals(expectedStatus, aPayment.getStatus());
        assertEquals(expectedInstant, aPayment.getTimestamp());
    }

}