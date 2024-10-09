package com.fiap.tech.ordereditens.infra.persistence;

import com.fiap.tech.categories.infra.CategoryJpaEntity;
import com.fiap.tech.order.infra.persistence.OrderJpaEntity;
import com.fiap.tech.ordereditens.domain.OrderedItem;
import com.fiap.tech.ordereditens.domain.OrderedItemID;
import com.fiap.tech.product.domain.ProductID;
import com.fiap.tech.product.infra.persistense.ProductJpaEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity(name = "OrderedItem")
@Table(name = "ordered_item")
public class OrderedItemJpaEntity {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "subTotal", nullable = false)
    private BigDecimal price;
    @Column(name = "product_id", nullable = false)
    private String productId;


    @ManyToOne(targetEntity = ProductJpaEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private ProductJpaEntity product;
    public OrderedItemJpaEntity() {
    }

    public OrderedItemJpaEntity(String id, Integer quantity, BigDecimal price, String productId) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.productId = productId;

    }

    public static OrderedItemJpaEntity from(final OrderedItem aOrderedItem) {
        return new OrderedItemJpaEntity(aOrderedItem.getId().getValue(), aOrderedItem.getQuantity(), aOrderedItem.getPrice(), aOrderedItem.getProduct().getValue());
    }

    public OrderedItem toAggregate() {
        return OrderedItem.with(OrderedItemID.from(this.id), ProductID.from(this.productId), this.quantity, this.price);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public ProductJpaEntity getProduct() {
        return product;
    }
    public void setProduct(ProductJpaEntity product) {
        this.product = product;
    }
}
