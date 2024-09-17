package com.fiap.tech.payment.webhook;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook")
@Tag(name = "Webhook Controller", description = "Receives payment notifications")
public class WebhookController {

    @PostMapping("/payment")
    @Operation(summary = "Receives payment notifications")
    public ResponseEntity<String> handlePaymentNotification(@RequestBody PaymentNotification notification) {

        if ("approved".equals(notification.getStatus())) {

            System.out.println("Payment approved: " + notification.getPaymentId());
        } else if ("rejected".equals(notification.getStatus())) {

            System.out.println("Payment rejected: " + notification.getPaymentId());
        }


        return ResponseEntity.ok("Notification received successfully");
    }
}
