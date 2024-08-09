package com.fiap.tech.product.domain;

import com.fiap.tech.categories.domain.CategoryID;
import com.fiap.tech.common.domain.AggregateRoot;
import com.fiap.tech.common.domain.exceptions.NotificationException;
import com.fiap.tech.common.domain.validation.ValidationHandler;
import com.fiap.tech.common.domain.validation.handler.Notification;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class Product extends AggregateRoot<ProductID> {


    private String name;

    private String description;

    private BigDecimal price;

    private CategoryID category;

    private Product(
            final ProductID anId,
            final String aName,
            final String aDescription,
            final BigDecimal aPrice,
            final CategoryID aCategoryId
    ) {
        super(anId);
        this.name = aName;
        this.description = aDescription;
        this.price = aPrice;
        this.category = aCategoryId;

        selfValidate();
    }

    private void selfValidate() {
        final var notification = Notification.create();

        validate(notification);

        if(notification.hasErrors()) {
            throw new NotificationException("", notification);
        }
    }
    public static Product newProduct (
            final String aName,
            final String aDescription,
            final BigDecimal aPrice
    ) {
        final var id = ProductID.unique();
        return new Product(id, aName, aDescription, aPrice, null);

    }
    public static Product with(Product aProduct) {
        return with(
                aProduct.getId(),
                aProduct.getName(),
                aProduct.getDescription(),
                aProduct.getPrice(),
                aProduct.getCategory()
        );
    }

    public static Product with(
            final ProductID anId,
            final String aName,
            final String aDescription,
            final BigDecimal aPrice,
            final CategoryID aCategoryId
    ) {
        return new Product(
                anId,
                aName,
                aDescription,
                aPrice,
                aCategoryId
        );
    }

    public Product update(final String aName, final String aDescription, final BigDecimal aPrice) {
        this.name = aName;
        this.description = aDescription;
        this.price = aPrice;

        selfValidate();

        return this;
    }

    @Override
    public void validate(ValidationHandler handler) {
        new ProductValidator(this, handler).validate();
    }
}
