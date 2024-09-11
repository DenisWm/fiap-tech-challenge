package com.fiap.tech.ordereditens.domain;

import com.fiap.tech.common.domain.AggregateRoot;
import com.fiap.tech.common.domain.validation.ValidationHandler;
import com.fiap.tech.order.domain.OrderID;
import com.fiap.tech.product.domain.ProductID;

import java.math.BigDecimal;

public class OrderedItem extends AggregateRoot<OrderedItemID> {

    private ProductID product;
    private String productName;
    private BigDecimal productPrice;
    private int quantity;
    private BigDecimal price;

    private OrderedItem(
            final OrderedItemID id,
            final ProductID product,
            final String productName,
            final BigDecimal productPrice,
            final int quantity,
            final BigDecimal price
    ) {
        super(id);
        this.product = product;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.price = price;
    }

    public static OrderedItem newOrderedItem(
            final ProductID product,
            final String productName,
            final BigDecimal productPrice,
            final int quantity,
            final BigDecimal price
    ) {
        final var id = OrderedItemID.unique();
        return new OrderedItem(id, product, productName, productPrice, quantity, price);
    }

    public static OrderedItem with(
            final OrderedItemID id,
            final ProductID product,
            final String productName,
            final BigDecimal productPrice,
            final int quantity,
            final BigDecimal price
    ) {
        return new OrderedItem(id, product, productName, productPrice, quantity, price);
    }

    public static OrderedItem with(final OrderedItem orderedItem) {
        return with(
                orderedItem.getId(),
                orderedItem.getProduct(),
                orderedItem.getProductName(),
                orderedItem.getProductPrice(),
                orderedItem.getQuantity(),
                orderedItem.getPrice()
        );
    }

    public BigDecimal getTotalPrice() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public void validate(ValidationHandler handler) {

    }

    public ProductID getProduct() {
        return product;
    }

    public void setProduct(ProductID product) {
        this.product = product;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}