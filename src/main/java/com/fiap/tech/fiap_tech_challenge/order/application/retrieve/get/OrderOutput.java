package com.fiap.tech.fiap_tech_challenge.order.application.retrieve.get;

import com.fiap.tech.fiap_tech_challenge.order.domain.Order;
import com.fiap.tech.fiap_tech_challenge.order.domain.OrderStatus;
import com.fiap.tech.fiap_tech_challenge.product.domain.Product;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
@Getter
public class OrderOutput {
    private String id;
    private Instant timestamp;
    private BigDecimal total;
    private List<OrderProductOutput> products;
    private String clientId;
    private OrderStatus status;

    public OrderOutput(String id, Instant timestamp, BigDecimal total, List<OrderProductOutput> products, String clientId, OrderStatus status) {
        this.id = id;
        this.timestamp = timestamp;
        this.total = total;
        this.products = products;
        this.clientId = clientId;
        this.status = status;
    }


    public static OrderOutput from(final Order aOrder) {
        return new OrderOutput(
                aOrder.getId().getValue(),
                aOrder.getTimestamp(),
                aOrder.getTotal(),
                new ArrayList<>(),
                aOrder.getClientId().getValue(),
                aOrder.getStatus()
        );
    }

    public OrderOutput withProducts(final List<OrderProductOutput> products) {
        return new OrderOutput(id, timestamp, total, products, clientId, status);
    }

    public List<OrderProductOutput> getProducts() {
        return products;
    }

    public record OrderProductOutput(String id, BigDecimal price, String name) {
        public static OrderProductOutput from(final Product aProduct) {
            return new OrderProductOutput(
                    aProduct.getId().getValue(),
                    aProduct.getPrice(),
                    aProduct.getName()
            );
        }
    }
}
