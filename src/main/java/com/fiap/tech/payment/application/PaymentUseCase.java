package com.fiap.tech.payment.application;

import com.fiap.tech.order.domain.Order;
import com.fiap.tech.order.domain.OrderID;
import com.fiap.tech.payment.domain.Payment;
import com.fiap.tech.payment.domain.PaymentGateway;
import com.fiap.tech.payment.domain.PaymentID;
import com.fiap.tech.payment.domain.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Service
public class PaymentUseCase {

    private final PaymentGateway paymentGateway;

    @Autowired
    public PaymentUseCase(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public void processPayment(Order order) {
        // Simula o envio de detalhes do pagamento para o serviço (pode ser externo)
        System.out.println("Sending payment details for order: " + order.getId());

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                 .uri(URI.create("http://localhost:8080/webhook/payment"))
                 .POST(HttpRequest.BodyPublishers.ofString(order.toString()))
                 .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Response from payment service: " + response.body());
    }

    public Optional<Payment> findPaymentById(PaymentID paymentID) {
        return paymentGateway.findById(paymentID);
    }

    public Optional<Order> findOrderById(OrderID orderID) {
        return paymentGateway.findOrderById(orderID);
    }

    public void approvePayment(PaymentID paymentId) {
        Optional<Payment> payment = paymentGateway.findById(paymentId);
        if (payment != null) {
            payment.get().setStatus(PaymentStatus.APPROVED);
            paymentGateway.save(payment);
        }
    }

    // Gets the payment status for a given order
    public PaymentStatus getPaymentStatus(Order order) {
        // Simulates obtaining the payment status
        // Here you can add logic to get the real payment status
        // For example, by querying an external service or checking the order state
        String status = "approved"; // or "rejected", "pending", etc.

        switch (status) {
            case "approved":
                return PaymentStatus.APPROVED;
            case "rejected":
                return PaymentStatus.REJECTED;
            case "pending":
                return PaymentStatus.PENDING;
            case "failed":
                return PaymentStatus.FAILED;
            case "cancelled":
                return PaymentStatus.CANCELLED;
            case "refunded":
                return PaymentStatus.REFUNDED;
            case "in review":
                return PaymentStatus.IN_REVIEW;
            case "completed":
                return PaymentStatus.COMPLETED;
            case "expired":
                return PaymentStatus.EXPIRED;
            default:
                throw new IllegalArgumentException("Unknown payment status: " + status);
        }
    }

    public void updatePaymentStatus(Optional<Payment> payment) {
    }
}
