package com.fiap.tech.order.infra.persistence;

import com.fiap.tech.ordereditens.domain.OrderedItemID;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ordered_items")
public class OrderOrderedItemJpaEntity {
    @EmbeddedId
    private OrderOrderedItemID id;

    @ManyToOne
    @MapsId("orderId")
    private OrderJpaEntity order;

    public OrderOrderedItemJpaEntity() {
    }

    public OrderOrderedItemJpaEntity(final OrderJpaEntity anOrder, final OrderedItemID orderedItemId) {
        this.id = OrderOrderedItemID.from(anOrder.getId(), orderedItemId.getValue());
        this.order = anOrder;
    }

    public static OrderOrderedItemJpaEntity from(final OrderJpaEntity anOrder, final OrderedItemID orderedItemId) {
        return new OrderOrderedItemJpaEntity(anOrder, orderedItemId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderOrderedItemJpaEntity that = (OrderOrderedItemJpaEntity) o;

        if (!Objects.equals(id, that.id)) return false;
        return Objects.equals(order, that.order);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (order != null ? order.hashCode() : 0);
        return result;
    }

    public OrderOrderedItemID getId() {
        return id;
    }

    public void setId(OrderOrderedItemID id) {
        this.id = id;
    }

    public OrderJpaEntity getOrder() {
        return order;
    }

    public void setOrder(OrderJpaEntity order) {
        this.order = order;
    }
}
