package com.fiap.tech.payment.infra.api.controllers.webhook;

import com.fiap.tech.payment.application.PaymentUseCase;
import com.fiap.tech.order.domain.Order;
import com.fiap.tech.order.domain.OrderID;
import com.fiap.tech.payment.domain.Payment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/payment-notification")
    @Operation(summary = "Receives payment notifications")
    public ResponseEntity<String> handlePaymentNotification(@RequestBody PaymentNotification notification) {
       // try {

            //MercadoPagoConfig.configure();

            OrderID orderId = new OrderID(notification.getOrderId());
            Optional<Order> orderOpt = paymentUseCase.findOrderById(orderId);

            if (orderOpt.isPresent()) {
                Order order = orderOpt.get();
                if ("approved".equalsIgnoreCase(notification.getStatus())) {
                    System.out.println("Payment approved for order: " + order.getId());
                    Optional<Payment> payment = paymentUseCase.findPaymentById(order.getPaymentId());
                    if (payment.isPresent()) {
                        paymentUseCase.approvePayment(payment.get().getId());
                        paymentUseCase.updatePaymentStatus(payment);
                        paymentUseCase.processPayment(order);
                    } else {
                        System.out.println("Payment not found for order: " + order.getId());
                    }
                } else if ("rejected".equalsIgnoreCase(notification.getStatus())) {
                    System.out.println("Payment rejected for order: " + order.getId());
                    // Atualize o status do pedido ou execute outras ações necessárias
                }
            } else {
                System.out.println("Order not found: " + notification.getOrderId());
            }

            return ResponseEntity.ok("Notification received successfully");
     //   } catch (MPException e) {
     //       e.printStackTrace();
     //       return ResponseEntity.status(500).body("Error configuring Mercado Pago: " + e.getMessage());
        //}
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get payment status for a specific order")
    public ResponseEntity<String> getPaymentStatus(@PathVariable String orderId) {
        OrderID id = new OrderID(orderId);
        Optional<Order> orderOpt = paymentUseCase.findOrderById(id);

        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            String status = String.valueOf(order.getStatus());
            return ResponseEntity.ok("Payment status for order " + orderId + ": " + status);
        } else {
            return ResponseEntity.status(404).body("Order not found: " + orderId);
        }
    }
}
