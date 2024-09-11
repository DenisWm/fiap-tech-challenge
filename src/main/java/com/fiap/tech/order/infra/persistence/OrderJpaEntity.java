package com.fiap.tech.order.infra.persistence;

import com.fiap.tech.client.domain.ClientID;
import com.fiap.tech.order.domain.Order;
import com.fiap.tech.order.domain.OrderID;
import com.fiap.tech.order.domain.OrderStatus;
import com.fiap.tech.ordereditens.domain.OrderedItemID;
import com.fiap.tech.ordereditens.infra.persistence.OrderedItemJpaEntity;
import com.fiap.tech.product.domain.ProductID;
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
    private String status;    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<OrderOrderedItemJpaEntity> orderedItems;
    public OrderJpaEntity() {
    }

    public OrderJpaEntity(String id, Instant timestamp, String clientId, BigDecimal total, String status) {
        this.id = id;
        this.timestamp = timestamp;
        this.orderedItems = new HashSet<>();
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
        order.getOrderedItems().forEach(it -> entity.addOrderedItem(it));
        return entity;
    }

    public Order toAggregate() {
        return Order.with(
                OrderID.from(this.id),
                this.timestamp,
                this.total,
                getOrderedItemIDs(),
                this.clientId != null ? ClientID.from(this.clientId) : null,
                this.status != null ? OrderStatus.valueOf(this.status) : null
        );
    }

    private void addOrderedItem(final OrderedItemID anId) {
        this.orderedItems.add(OrderOrderedItemJpaEntity.from(this, anId));
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

    public Set<OrderOrderedItemJpaEntity> getOrderedItems() {
        return orderedItems;
    }

    public List<OrderedItemID> getOrderedItemIDs() {
        return orderedItems.stream()
                .map(it -> OrderedItemID.from(it.getId().getOrderedItemId())).toList();
    }
    public void setOrderedItems(Set<OrderOrderedItemJpaEntity> orderedItems) {
        this.orderedItems = orderedItems;
    }
}