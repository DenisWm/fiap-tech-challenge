package com.fiap.tech.payment.infra.api.controllers.webhook;

import com.fiap.tech.order.application.create.CreateOrderCommand;
import com.fiap.tech.order.application.create.CreateOrderOutput;
import com.fiap.tech.order.application.create.CreateOrderUseCase;
import com.fiap.tech.order.domain.Order;
import com.fiap.tech.order.domain.OrderID;
import com.fiap.tech.payment.application.PaymentUseCase;
import com.fiap.tech.order.infra.api.controllers.OrderController;
import com.fiap.tech.order.infra.models.CreateOrderRequest;
import com.fiap.tech.order.infra.models.ItemsRequest;
import com.fiap.tech.ordereditens.domain.OrderedItemID;
import com.fiap.tech.client.domain.ClientID;
import com.fiap.tech.payment.domain.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WebhookControllerTest {

    @InjectMocks
    private WebhookController webhookController;

    @Mock
    private PaymentUseCase paymentUseCase;

    @Mock
    private CreateOrderUseCase createOrderUseCase;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPaymentStatus() {
        // Arrange
        String orderId = "123";
        List<ItemsRequest> items = List.of(new ItemsRequest("prod123", 2), new ItemsRequest("prod456", 3));
        CreateOrderRequest request = new CreateOrderRequest("client123", items);
        CreateOrderOutput orderOutput = CreateOrderOutput.from(orderId);

        // Mock the behavior of the createOrderUseCase
        when(createOrderUseCase.execute(any(CreateOrderCommand.class))).thenReturn(orderOutput);

        // Create the order
        ResponseEntity<?> createOrderResponse = orderController.createOrder(request);
        assertEquals(HttpStatus.CREATED, createOrderResponse.getStatusCode());

        List<OrderedItemID> orderedItems = items.stream()
                .map(item -> OrderedItemID.from(item.productId()))
                .collect(Collectors.toList());

        // Create an order instance using the provided constructor
        Order order = Order.newOrder(
                BigDecimal.valueOf(100.00), // Usando BigDecimal.valueOf para criar o valor total
                orderedItems, // Lista de itens pedidos
                new ClientID("client123") // ID do cliente
        );

        // Mock the behavior of the payment status
        PaymentStatus mockPaymentStatus = mock(PaymentStatus.class);
        when(mockPaymentStatus.toString()).thenReturn("RECEIVED");
        when(paymentUseCase.getPaymentStatus(order)).thenReturn(mockPaymentStatus);

        // Act
        when(paymentUseCase.findOrderById(any(OrderID.class))).thenReturn(Optional.of(order));
        ResponseEntity<String> responseEntity = webhookController.getPaymentStatus(orderId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Payment status for order 123: RECEIVED", "Payment status for order 123: " + mockPaymentStatus.toString());
    }


    @Test
    public void testGetPaymentStatus_OrderNotFound() {
        // Arrange
        String orderId = "123";
        when(paymentUseCase.findOrderById(any(OrderID.class))).thenReturn(Optional.empty());

        // Act
        ResponseEntity<String> responseEntity = webhookController.getPaymentStatus(orderId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Order not found: 123", responseEntity.getBody());
    }
}
