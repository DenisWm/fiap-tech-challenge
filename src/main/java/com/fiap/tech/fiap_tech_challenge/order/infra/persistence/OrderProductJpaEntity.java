package com.fiap.tech.fiap_tech_challenge.order.infra.persistence;

import com.fiap.tech.fiap_tech_challenge.product.domain.ProductID;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "orders_products")
public class OrderProductJpaEntity {
    @EmbeddedId
    private OrderProductID id;

    @ManyToOne
    @MapsId("orderId")
    private OrderJpaEntity order;

    public OrderProductJpaEntity() {
    }

    public OrderProductJpaEntity(final OrderJpaEntity anOrder, final ProductID aProductID) {
        this.id = OrderProductID.from(anOrder.getId(), aProductID.getValue());
        this.order = anOrder;
    }

    public static OrderProductJpaEntity from(final OrderJpaEntity anOrder, final ProductID aProductID) {
        return new OrderProductJpaEntity(anOrder, aProductID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderProductJpaEntity that = (OrderProductJpaEntity) o;

        if (!Objects.equals(id, that.id)) return false;
        return Objects.equals(order, that.order);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (order != null ? order.hashCode() : 0);
        return result;
    }

    public OrderProductID getId() {
        return id;
    }

    public void setId(OrderProductID id) {
        this.id = id;
    }

    public OrderJpaEntity getOrder() {
        return order;
    }

    public void setOrder(OrderJpaEntity order) {
        this.order = order;
    }
}
