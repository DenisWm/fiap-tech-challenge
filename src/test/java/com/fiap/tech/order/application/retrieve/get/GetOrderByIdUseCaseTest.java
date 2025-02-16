package com.fiap.tech.order.application.retrieve.get;

import com.fiap.tech.client.application.UseCaseTest;
import com.fiap.tech.client.domain.ClientGateway;
import com.fiap.tech.order.domain.Order;
import com.fiap.tech.order.domain.OrderGateway;
import com.fiap.tech.order.domain.OrderStatus;
import com.fiap.tech.ordereditens.domain.OrderedItem;
import com.fiap.tech.ordereditens.domain.OrderedItemGateway;
import com.fiap.tech.ordereditens.domain.OrderedItemID;
import com.fiap.tech.product.domain.Product;
import com.fiap.tech.product.domain.ProductGateway;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class GetOrderByIdUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultGetOrderByIdUseCase useCase;

    @Mock
    private OrderGateway orderGateway;

    @Mock
    private OrderedItemGateway orderedItemGateway;

    @Mock
    private ProductGateway productGateway;

    @Mock
    private ClientGateway clientGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(orderGateway, orderedItemGateway, productGateway, clientGateway);
    }

    @Test
    void givenValidId_whenCallsGetById_thenShouldReturnOrder() {
        final var expectedProduct = Product.newProduct("produt", "descr", BigDecimal.TEN);
        final var expectedQuantity = 3;
        final var expectedOrderedItem = OrderedItem.newOrderedItem(expectedProduct.getId(), expectedQuantity, BigDecimal.TEN);
        final var expectedTotal = BigDecimal.TEN;

        final var order = Order.newOrder(expectedTotal, List.of(expectedOrderedItem.getId()), null);

        final var expectedId = order.getId();

        when(orderGateway.findById(any())).thenReturn(Optional.of(order));
        when(orderedItemGateway.findByIds(any())).thenReturn(List.of(expectedOrderedItem));
        when(productGateway.findByIds(any())).thenReturn(List.of(expectedProduct));
        when(clientGateway.findById(any())).thenReturn(Optional.empty());

        final var aResult = this.useCase.execute(expectedId.getValue());

        assertNotNull(aResult);
        assertEquals(order.getId().getValue(), aResult.getId());
        assertEquals(order.getTotal(), aResult.getTotal());
        assertTrue(order.getOrderedItems().size() == aResult.getOrderedItems().size());
        assertEquals(OrderStatus.RECEIVED, aResult.getStatus());
    }
}