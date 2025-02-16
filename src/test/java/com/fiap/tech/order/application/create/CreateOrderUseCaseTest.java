package com.fiap.tech.order.application.create;

import com.fiap.tech.client.application.UseCaseTest;
import com.fiap.tech.client.domain.ClientGateway;
import com.fiap.tech.client.domain.ClientID;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
}