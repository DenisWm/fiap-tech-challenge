package com.fiap.tech.payment.application.receive;

import com.fiap.tech.client.application.UseCaseTest;
import com.fiap.tech.client.domain.ClientID;
import com.fiap.tech.order.domain.Order;
import com.fiap.tech.order.domain.OrderGateway;
import com.fiap.tech.ordereditens.domain.OrderedItem;
import com.fiap.tech.ordereditens.domain.OrderedItemGateway;
import com.fiap.tech.payment.domain.Payment;
import com.fiap.tech.payment.domain.PaymentGateway;
import com.fiap.tech.payment.domain.PaymentID;
import com.fiap.tech.payment.domain.PaymentStatus;
import com.fiap.tech.product.domain.Product;
import com.fiap.tech.product.domain.ProductGateway;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReceivePaymentUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultReceivePaymentUseCase useCase;

    @Mock
    private PaymentGateway paymentGateway;

    @Mock
    private OrderGateway orderGateway;

    @Mock
    private ProductGateway productGateway;

    @Mock
    private OrderedItemGateway orderedItemGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(
                paymentGateway,
                orderGateway,
                productGateway,
                orderedItemGateway
        );
    }

    @Test
    void givenValidCmd_whenCallsReceive_shouldReturnId() {
        final var expectedId = PaymentID.unique();
        final var expectedClientId = "clientId";
        final var expectedAmount = BigDecimal.valueOf(10);
        final var expectedCreatedAt = Instant.now();
        final var expectedStatus = PaymentStatus.APPROVED;
        final var aProduct = Product.newProduct("product", "product", BigDecimal.valueOf(10));

        final var aOrderedItem = OrderedItem.newOrderedItem(aProduct.getId(), 1, BigDecimal.valueOf(10));

        final var aOrder = Order.newOrder(BigDecimal.valueOf(123), List.of(aOrderedItem.getId()), ClientID.from(expectedClientId));
        final var expectedOrderId = aOrder.getId().getValue();

        when(orderGateway.findById(any())).thenReturn(Optional.of(aOrder));
        when(orderGateway.update(any())).thenReturn(aOrder);
        when(paymentGateway.create(any())).thenAnswer(returnsFirstArg());
        when(orderedItemGateway.findByIds(any())).thenReturn(List.of(aOrderedItem));
        when(productGateway.findByIds(any())).thenReturn(List.of(aProduct));

        final var aCmd = ReceivePaymentCommand.with(
                expectedId.getValue(),
                expectedOrderId,
                expectedClientId,
                expectedAmount,
                expectedCreatedAt,
                expectedStatus
        );

        final var aResult = useCase.execute(aCmd);

        assertNotNull(aResult);
        assertEquals(expectedId.getValue(), aResult.id());

        final var argumentCaptor = ArgumentCaptor.forClass(Payment.class);

        verify(paymentGateway).create(argumentCaptor.capture());

        final var aPayment = argumentCaptor.getValue();
        assertNotNull(aPayment);
        assertEquals(expectedId, aPayment.getId());
        assertEquals(expectedAmount, aPayment.getAmount());
        assertEquals(expectedStatus, aPayment.getStatus());
        assertEquals(expectedId, aOrder.getPaymentId());
        assertEquals(expectedOrderId, aOrder.getId().getValue());
        assertEquals(expectedClientId, aOrder.getClientId().getValue());
        assertEquals(expectedCreatedAt, aPayment.getTimestamp());

    }
}