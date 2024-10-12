package com.fiap.tech.order.infra.api.controllers;

import com.fiap.tech.common.domain.pagination.Pagination;
import com.fiap.tech.order.application.create.CreateOrderCommand;
import com.fiap.tech.order.application.create.CreateOrderUseCase;
import com.fiap.tech.order.application.create.ItemCommand;
import com.fiap.tech.order.application.retrieve.get.GetOrderByIdUseCase;
import com.fiap.tech.order.application.retrieve.list.ListOrderUseCase;
import com.fiap.tech.order.application.update.UpdateOrderCommand;
import com.fiap.tech.order.application.update.UpdateOrderUseCase;
import com.fiap.tech.order.infra.api.OrderAPI;
import com.fiap.tech.order.infra.models.CreateOrderRequest;
import com.fiap.tech.order.infra.models.ListOrderResponse;
import com.fiap.tech.order.infra.models.OrderResponse;
import com.fiap.tech.order.infra.models.UpdateOrderRequest;
import com.fiap.tech.product.application.retrieve.list.OrderSearchQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class OrderController implements OrderAPI {

    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderByIdUseCase getOrderByIdUseCase;
    private final ListOrderUseCase listOrderUseCase;
    private final UpdateOrderUseCase updateOrderUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase, GetOrderByIdUseCase getOrderByIdUseCase, ListOrderUseCase listOrderUseCase, UpdateOrderUseCase updateOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.getOrderByIdUseCase = getOrderByIdUseCase;
        this.listOrderUseCase = listOrderUseCase;
        this.updateOrderUseCase = updateOrderUseCase;
    }

    @Override
    public ResponseEntity<?> createOrder(final CreateOrderRequest input) {
        final var aCommand = CreateOrderCommand.with(
                input.clientId(),
                input.items().stream()
                        .map(item -> ItemCommand.with(item.productId(), item.quantity()))
                        .toList()
        );
        final var output = this.createOrderUseCase.execute(aCommand);
        return ResponseEntity.created(URI.create("/orders/" + output.id())).body(output);
    }

    @Override
    public ResponseEntity<?> updateOrderById(String id, UpdateOrderRequest input) {
        final var command = UpdateOrderCommand.with(id, input.status());

        final var output = updateOrderUseCase.execute(command);

        return ResponseEntity.ok(output);
    }

    @Override
    public Pagination<ListOrderResponse> listOrders(
            final String clientId,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        final var aQuery = new OrderSearchQuery(page, perPage, clientId, sort, direction);
        return this.listOrderUseCase.execute(aQuery).map(ListOrderResponse::from);
    }

    @Override
    public OrderResponse getById(final String id) {
        return OrderResponse.from(this.getOrderByIdUseCase.execute(id)) ;
    }
}
