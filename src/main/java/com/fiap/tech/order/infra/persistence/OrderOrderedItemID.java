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

}
