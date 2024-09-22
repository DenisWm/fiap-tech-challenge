package com.fiap.tech.payment.application;

import com.fiap.tech.order.domain.Order;
import com.fiap.tech.order.domain.OrderID;
import com.fiap.tech.payment.domain.PaymentGateway;
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

    public Optional<Order> findOrderById(OrderID orderId) {
        return paymentGateway.findOrderById(orderId);
    }
}
