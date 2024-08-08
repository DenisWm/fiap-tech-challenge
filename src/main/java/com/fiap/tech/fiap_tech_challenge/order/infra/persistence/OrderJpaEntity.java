package com.fiap.tech.fiap_tech_challenge.order.infra.persistence;

import com.fiap.tech.fiap_tech_challenge.client.domain.ClientID;
import com.fiap.tech.fiap_tech_challenge.order.domain.Order;
import com.fiap.tech.fiap_tech_challenge.order.domain.OrderID;
import com.fiap.tech.fiap_tech_challenge.order.domain.OrderStatus;
import com.fiap.tech.fiap_tech_challenge.product.domain.ProductID;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "Order")
@Table(name = "orders")
public class OrderJpaEntity {

    @Id
    private String id;
    private Instant timestamp;
    private String clientId;
    private BigDecimal total;
    private String status;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<OrderProductJpaEntity> products;


    public OrderJpaEntity() {
    }

    public OrderJpaEntity(String id, Instant timestamp, String clientId, BigDecimal total, String status) {
        this.id = id;
        this.timestamp = timestamp;
        this.products = new HashSet<>();
        this.clientId = clientId;
        this.total = total;
        this.status = status;
    }


    public static OrderJpaEntity from(final Order order) {
        final var entity = new OrderJpaEntity(
                order.getId().getValue(),
                order.getTimestamp(),
                order.getClientId() != null ? order.getClientId().getValue() : null,
                order.getTotal(),
                order.getStatus().getValue()
        );
        order.getProducts().forEach(entity::addProduct);
        return entity;
    }

    public Order toAggregate() {
        return Order.with(
                OrderID.from(this.id),
                this.timestamp,
                this.total,
                getProductIDs(),
                this.clientId != null ? ClientID.from(this.clientId) : null,
                this.status != null ? OrderStatus.valueOf(this.status) : null
        );
    }

    private void addProduct(final ProductID anId) {
        this.products.add(OrderProductJpaEntity.from(this, anId));
    }

    public String getId() {
        return id;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getClientId() {
        return clientId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Set<OrderProductJpaEntity> getProducts() {
        return products;
    }

    public List<ProductID> getProductIDs() {
        return products.stream()
                .map(it -> ProductID.from(it.getId().getProductId())).toList();
    }
    public void setProducts(Set<OrderProductJpaEntity> products) {
        this.products = products;
    }
}
