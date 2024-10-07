package com.fiap.tech.order.infra.api.controllers;

import com.fiap.tech.order.application.create.CreateOrderCommand;
import com.fiap.tech.order.application.create.CreateOrderOutput;
import com.fiap.tech.order.application.create.CreateOrderUseCase;
import com.fiap.tech.order.infra.models.CreateOrderRequest;
import com.fiap.tech.order.infra.models.ItemsRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private CreateOrderUseCase createOrderUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProduct() {
        // Arrange
        List<ItemsRequest> items = List.of(new ItemsRequest("0074a3a3-e7f1-4313-bd4b-8f4384d51e6e", 2), new ItemsRequest("5f61ac2f-c80f-4938-8836-3ea3e8cf85b8", 3));
        CreateOrderRequest request = new CreateOrderRequest("client123", items);
        CreateOrderOutput output = CreateOrderOutput.from("1");

        // Mock the behavior of the use case
        when(createOrderUseCase.execute(any(CreateOrderCommand.class))).thenReturn(output);

        // Act
        ResponseEntity<?> responseEntity = orderController.createProduct(request);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(output, responseEntity.getBody());
    }
}
