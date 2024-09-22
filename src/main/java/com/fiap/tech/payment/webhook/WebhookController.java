package com.fiap.tech.payment.webhook;

import com.fiap.tech.payment.application.PaymentUseCase;
import com.fiap.tech.order.domain.Order;
import com.fiap.tech.order.domain.OrderID;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/webhook")
@Tag(name = "Webhook Controller", description = "Receives payment notifications")
public class WebhookController {

    private final PaymentUseCase paymentUseCase;

    @Autowired
    public WebhookController(PaymentUseCase paymentUseCase) {
        this.paymentUseCase = paymentUseCase;
    }

    @PostMapping("/payment")
    @Operation(summary = "Receives payment notifications")
    public ResponseEntity<String> handlePaymentNotification(@RequestBody PaymentNotification notification) {
        OrderID orderId = new OrderID(notification.getOrderId());
        Optional<Order> orderOpt = paymentUseCase.findOrderById(orderId);

        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            if ("approved".equals(notification.getStatus())) {
                System.out.println("Payment approved for order: " + order.getId());
                paymentUseCase.processPayment(order);
                // Aqui está mockado, se necessário atualizar o status do pedido ou execute outras ações necessárias
            } else if ("rejected".equals(notification.getStatus())) {
                System.out.println("Payment rejected for order: " + order.getId());
                // Aqui está mockado, se necessário atualizar o status do pedido ou execute outras ações necessárias
            }
        } else {
            System.out.println("Order not found: " + notification.getOrderId());
        }

        return ResponseEntity.ok("Notification received successfully");
    }
}
