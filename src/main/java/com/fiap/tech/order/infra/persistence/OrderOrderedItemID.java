package com.fiap.tech.order.infra.persistence;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderOrderedItemID implements Serializable {
    @Column(name = "order_id", nullable = false)
    private String orderId;
    @Column(name = "ordered_item_id", nullable = false)
    private String orderedItemId;

    public OrderOrderedItemID() {
    }

    private OrderOrderedItemID(final String aOrderId, final String orderedItemId) {
        this.orderId = aOrderId;
        this.orderedItemId = orderedItemId;
    }

    public static OrderOrderedItemID from(final String aOrderId, final String orderedItemId) {
        return new OrderOrderedItemID(aOrderId, orderedItemId);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderedItemId() {
        return orderedItemId;
    }

    public void setOrderedItemId(String orderedItemId) {
        this.orderedItemId = orderedItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderOrderedItemID that = (OrderOrderedItemID) o;

        if (!Objects.equals(orderId, that.orderId)) return false;
        return Objects.equals(orderedItemId, that.orderedItemId);
    }

    @Override
    public int hashCode() {
        int result = orderId != null ? orderId.hashCode() : 0;
        result = 31 * result + (orderedItemId != null ? orderedItemId.hashCode() : 0);
        return result;
    }
}
