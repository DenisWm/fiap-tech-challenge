package com.fiap.tech.order.application.create;

import com.fiap.tech.client.application.UseCaseTest;
import com.fiap.tech.client.domain.ClientGateway;
import com.fiap.tech.client.domain.ClientID;
import com.fiap.tech.common.domain.exceptions.DomainException;
import com.fiap.tech.common.domain.exceptions.NotificationException;
import com.fiap.tech.order.domain.Order;
import com.fiap.tech.order.domain.OrderGateway;
import com.fiap.tech.ordereditens.domain.OrderedItem;
import com.fiap.tech.ordereditens.domain.OrderedItemGateway;
import com.fiap.tech.product.domain.Product;
import com.fiap.tech.product.domain.ProductGateway;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateOrderUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCreateOrderUseCase useCase;

    @Mock
    private OrderGateway orderGateway;

    @Mock
    private ClientGateway clientGateway;

    @Mock
    private ProductGateway productGateway;

    @Mock
    private OrderedItemGateway orderedItemGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(orderGateway, clientGateway, productGateway, orderedItemGateway);
    }

    @Test
    void givenValidCmd_whenCallsCreate_thenShouldSuccess() {
        final var aProduct = Product.newProduct("Product", "Description", BigDecimal.TEN);
        final var expectedClientId = ClientID.unique();
        final var expectedItems = List.of(ItemCommand.with(aProduct.getId().getValue(), 5));

        when(clientGateway.existsByID(any())).thenReturn(true);
        when(productGateway.existsByIds(any())).thenReturn(List.of(aProduct.getId()));
        when(productGateway.findByIds(any())).thenReturn(List.of(aProduct));
        when(orderedItemGateway.create(any())).thenAnswer(returnsFirstArg());
        when(orderGateway.create(any())).thenAnswer(returnsFirstArg());

        final var aCmd = CreateOrderCommand.with(expectedClientId.getValue(), expectedItems);

        final var aResult = useCase.execute(aCmd);

        assertNotNull(aResult);
        assertNotNull(aResult.id());

        final var captorOrder = ArgumentCaptor.forClass(Order.class);
        final var captorOrderedItem = ArgumentCaptor.forClass(OrderedItem.class);

        verify(orderGateway).create(captorOrder.capture());
        verify(orderedItemGateway).create(captorOrderedItem.capture());

        final var aOrder = captorOrder.getValue();
        final var aOrderedItem = captorOrderedItem.getValue();

        assertNotNull(aOrder);
        assertEquals(expectedClientId, aOrder.getClientId());
        assertEquals(aProduct.getId(), aOrderedItem.getProduct());
        assertEquals(aProduct.getId(), aOrderedItem.getProduct());
        assertEquals(aProduct.getPrice(), aOrderedItem.getPrice());
        assertEquals(expectedItems.get(0).quantity(), aOrderedItem.getQuantity());
        assertEquals(expectedItems.get(0).productID(), aOrderedItem.getProduct().getValue());
    }

    @Test
    void givenValidCmdWithNullClientId_whenCallsCreate_thenShouldSuccess() {
        final var aProduct = Product.newProduct("Product", "Description", BigDecimal.TEN);
        final var expectedItems = List.of(ItemCommand.with(aProduct.getId().getValue(), 5));

        when(productGateway.existsByIds(any())).thenReturn(List.of(aProduct.getId()));
        when(productGateway.findByIds(any())).thenReturn(List.of(aProduct));
        when(orderedItemGateway.create(any())).thenAnswer(returnsFirstArg());
        when(orderGateway.create(any())).thenAnswer(returnsFirstArg());

        final var aCmd = CreateOrderCommand.with(null, expectedItems);

        final var aResult = useCase.execute(aCmd);

        assertNotNull(aResult);
        assertNotNull(aResult.id());

        final var captorOrder = ArgumentCaptor.forClass(Order.class);
        final var captorOrderedItem = ArgumentCaptor.forClass(OrderedItem.class);

        verify(orderGateway).create(captorOrder.capture());
        verify(orderedItemGateway).create(captorOrderedItem.capture());

        final var aOrder = captorOrder.getValue();
        final var aOrderedItem = captorOrderedItem.getValue();

        assertNotNull(aOrder);
        assertNull(aOrder.getClientId());
        assertEquals(aProduct.getId(), aOrderedItem.getProduct());
        assertEquals(aProduct.getId(), aOrderedItem.getProduct());
        assertEquals(aProduct.getPrice(), aOrderedItem.getPrice());
        assertEquals(expectedItems.get(0).quantity(), aOrderedItem.getQuantity());
        assertEquals(expectedItems.get(0).productID(), aOrderedItem.getProduct().getValue());
    }

    @Test
    void givenAnInvalidCmdWithNullItems_whenCallsCreate_thenShouldSuccess() {
        final var expectedClientId = ClientID.unique();
        final List<ItemCommand> expectedItems = null;
        final var expectedErrorMessage = "Order must have at least one product.";
        final var expectedErrorCount = 1;

        when(clientGateway.existsByID(any())).thenReturn(true);

        final var aCmd = CreateOrderCommand.with(expectedClientId.getValue(), expectedItems);

        final var aResult = assertThrows(DomainException.class, () -> useCase.execute(aCmd));

        assertNotNull(aResult);
        assertEquals(expectedErrorMessage, aResult.getErrors().get(0).message());
        assertEquals(expectedErrorCount, aResult.getErrors().size());
    }

    @Test
    void givenAnInvalidCmdWithEmptyItems_whenCallsCreate_thenShouldSuccess() {
        final var expectedClientId = ClientID.unique();
        final List<ItemCommand> expectedItems = new ArrayList<>();
        final var expectedErrorMessage = "Order must have at least one product.";
        final var expectedErrorCount = 1;

        when(clientGateway.existsByID(any())).thenReturn(true);

        final var aCmd = CreateOrderCommand.with(expectedClientId.getValue(), expectedItems);

        final var aResult = assertThrows(DomainException.class, () -> useCase.execute(aCmd));

        assertNotNull(aResult);
        assertEquals(expectedErrorMessage, aResult.getErrors().get(0).message());
        assertEquals(expectedErrorCount, aResult.getErrors().size());
    }
    @Test
    void givenValidCmdWithInexistentItem_whenCallsCreate_thenShouldSuccess() {
        final var aProductA = Product.newProduct("Product", "Description", BigDecimal.TEN);
        final var aProductB = Product.newProduct("Product", "Description", BigDecimal.TEN);
        final var expectedItems = List.of(ItemCommand.with(aProductA.getId().getValue(), 5),
                ItemCommand.with(aProductB.getId().getValue(), 3));

        final var expectedErrorMessage = "Some items couldn't be found: %s".formatted(aProductB.getId().getValue());
        final var expectedErrorCount = 1;
        when(productGateway.existsByIds(any())).thenReturn(List.of(aProductA.getId()));

        final var aCmd = CreateOrderCommand.with(null, expectedItems);

        final var aResult = assertThrows(DomainException.class, () -> useCase.execute(aCmd));

        assertNotNull(aResult);
        assertEquals(expectedErrorMessage, aResult.getErrors().get(0).message());
        assertEquals(expectedErrorCount, aResult.getErrors().size());
    }

}