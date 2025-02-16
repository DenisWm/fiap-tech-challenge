package com.fiap.tech.order.infra.persistence;

import com.fiap.tech.IntegrationTest;
import com.fiap.tech.categories.domain.Category;
import com.fiap.tech.categories.domain.CategoryID;
import com.fiap.tech.categories.infra.CategoryJpaEntity;
import com.fiap.tech.client.domain.ClientID;
import com.fiap.tech.order.application.create.ItemCommand;
import com.fiap.tech.order.domain.Order;
import com.fiap.tech.order.domain.event.OrderCreated;
import com.fiap.tech.order.domain.event.OrderPayed;
import com.fiap.tech.ordereditens.domain.OrderedItemID;
import com.fiap.tech.product.application.retrieve.list.OrderSearchQuery;
import com.fiap.tech.product.domain.Product;
import com.fiap.tech.product.domain.pagination.ProductSearchQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
class OrderPostgresGatewayTest {

    @Autowired
    private OrderPostgresGateway orderGateway;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void testInjection() {
        assertNotNull(orderGateway);
        assertNotNull(orderRepository);
    }

    @Test
    void givenValidSearchQuery_whenCallsFindAll_shouldReturnPagination() {
            final var order = Order.newOrder(BigDecimal.TEN, List.of(OrderedItemID.unique()), ClientID.unique());
            orderGateway.create(order);

            final var expectedPage = 0;
            final var expectedPerPage = 10;
            final var expectedTotal = 1;
            final var expectedClientId = "";
            final var expectedSort = "total";
            final var expectedDir = "asc";

            assertEquals(1, orderRepository.count());

            final var aQuery = new OrderSearchQuery(expectedPage, expectedPerPage, expectedClientId, expectedSort, expectedDir);

            final var aResult = orderGateway.findAll(aQuery);

            assertEquals(expectedPage, aResult.currentPage());
            assertEquals(expectedPerPage, aResult.perPage());
            assertEquals(expectedTotal, aResult.total());
            assertEquals(expectedTotal, aResult.items().size());
    }

    @Test
    void givenValidOrder_whenCallsCreate_shouldReturnOrder() {
        final var expectedTotal = BigDecimal.TEN;
        final var expectedClientId = ClientID.unique();
        final var expectedOrderId = OrderedItemID.unique();

        final var order = Order.newOrder(expectedTotal, List.of(expectedOrderId), expectedClientId);
        order.registerEvent(new OrderCreated(expectedOrderId.getValue(),expectedClientId.getValue(), expectedTotal));
        order.registerEvent(new OrderPayed(expectedOrderId.getValue(),null));
        assertEquals(0, orderRepository.count());

        final var aResult = orderGateway.create(order);

        assertEquals(order.getId().getValue(), aResult.getId().getValue());
        assertEquals(order.getTotal(), aResult.getTotal());
        assertEquals(order.getClientId(), aResult.getClientId());
        assertEquals(2, order.getDomainEvents().size());
    }

}