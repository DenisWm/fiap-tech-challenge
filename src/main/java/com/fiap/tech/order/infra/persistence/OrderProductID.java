package com.fiap.tech.order.infra.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderProductID implements Serializable {
    @Column(name = "order_id", nullable = false)
    private String orderId;
    @Column(name = "product_id", nullable = false)
    private String productId;


    public OrderProductID() {
    }

    private OrderProductID(final String aOrderId, final String aProductId) {
        this.orderId = aOrderId;
        this.productId = aProductId;
    }

    public static OrderProductID from(final String aOrderId, final String aProductId) {
        return new OrderProductID(aOrderId, aProductId);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderProductID that = (OrderProductID) o;

        if (!Objects.equals(orderId, that.orderId)) return false;
        return Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        int result = orderId != null ? orderId.hashCode() : 0;
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        return result;
    }
}
