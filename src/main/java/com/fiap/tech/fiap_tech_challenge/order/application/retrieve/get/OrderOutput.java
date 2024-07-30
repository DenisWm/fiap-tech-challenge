package com.fiap.tech.fiap_tech_challenge.order.application.retrieve.get;

import com.fiap.tech.fiap_tech_challenge.order.domain.Order;
import com.fiap.tech.fiap_tech_challenge.order.domain.OrderStatus;
import com.fiap.tech.fiap_tech_challenge.product.domain.Product;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OrderOutput {
    String id;
    Instant timestamp;
    BigDecimal total;
    List<ProductOutput> products;
    String clientId;
    OrderStatus status;

    public OrderOutput(String id, Instant timestamp, BigDecimal total, List<ProductOutput> products, String clientId, OrderStatus status) {
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
                aOrder.getClient().getValue(),
                aOrder.getStatus()
        );
    }

    public OrderOutput withProducts(List<ProductOutput> products) {
        return new OrderOutput(id, timestamp, total, products, clientId, status);
    }

    public record ProductOutput(String id, BigDecimal price, String name) {
        public static ProductOutput from(final Product aProduct) {
            return new ProductOutput(
                    aProduct.getId().getValue(),
                    aProduct.getPrice(),
                    aProduct.getName()
            );
        }

    }
}
