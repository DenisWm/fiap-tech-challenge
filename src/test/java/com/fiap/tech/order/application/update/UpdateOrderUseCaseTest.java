package com.fiap.tech.order.application.update;

import com.fiap.tech.client.application.UseCaseTest;
import com.fiap.tech.client.domain.ClientID;
import com.fiap.tech.common.domain.exceptions.NotFoundException;
import com.fiap.tech.order.application.create.ItemCommand;
import com.fiap.tech.order.domain.Order;
import com.fiap.tech.order.domain.OrderGateway;
import com.fiap.tech.order.domain.OrderStatus;
import com.fiap.tech.ordereditens.domain.OrderedItemID;
import com.fiap.tech.product.application.update.UpdateProductCommand;
import com.fiap.tech.product.domain.Product;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpdateOrderUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultUpdateOrderUseCase useCase;

    @Mock
    private OrderGateway orderGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(orderGateway);
    }

    @Test
    void givenValidParam_whenCallsUpdateOrder_thenShouldReturnId() {
        final var expectedClientId = ClientID.unique();
        final var expectedTotal = BigDecimal.TEN;
        final var expectedStatus = OrderStatus.IN_PREPARATION;
        final var expectedOrderedItemId = OrderedItemID.unique();

        final var aOrder = Order.newOrder(expectedTotal, List.of(expectedOrderedItemId), expectedClientId);

        final var expectedId = aOrder.getId();
        when(orderGateway.findById(any())).thenReturn(Optional.of(Order.with(aOrder)));
        when(orderGateway.update(any())).thenAnswer(returnsFirstArg());

        final var aCmd = UpdateOrderCommand.with(expectedId.getValue(), expectedStatus.getValue());

        final var aResult = useCase.execute(aCmd);

        assertNotNull(aResult);
        assertEquals(expectedId.getValue(), aResult.id());

        final var captor = ArgumentCaptor.forClass(Order.class);

        verify(orderGateway).update(captor.capture());

        final var actualOrder = captor.getValue();

        assertEquals(expectedTotal, actualOrder.getTotal());
        assertEquals(expectedStatus, actualOrder.getStatus());
        assertEquals(expectedClientId, actualOrder.getClientId());
        assertEquals(expectedId, actualOrder.getId());
        assertEquals(aOrder.getTimestamp(), actualOrder.getTimestamp());
        assertEquals(aOrder.getOrderedItems(), actualOrder.getOrderedItems());
    }

    @Test
    void givenInvalidId_whenCallsUpdateOrder_thenShouldReturnId() {
        final var expectedStatus = OrderStatus.IN_PREPARATION;
        final var expectedId = "123";
        final var expectedErrorMessage = "Order with ID 123 was not found";

        when(orderGateway.findById(any())).thenReturn(Optional.empty());

        final var aCmd = UpdateOrderCommand.with(expectedId, expectedStatus.getValue());

        final var aResult = assertThrows(NotFoundException.class, () -> useCase.execute(aCmd));

        assertNotNull(aResult);
        assertEquals(expectedErrorMessage, aResult.getMessage());
    }
}