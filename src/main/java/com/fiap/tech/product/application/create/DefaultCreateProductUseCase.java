package com.fiap.tech.product.application.create;

import com.fiap.tech.categories.domain.CategoryGateway;
import com.fiap.tech.categories.domain.CategoryID;
import com.fiap.tech.common.domain.exceptions.NotificationException;
import com.fiap.tech.common.domain.validation.Error;
import com.fiap.tech.common.domain.validation.ValidationHandler;
import com.fiap.tech.common.domain.validation.handler.Notification;
import com.fiap.tech.product.domain.Product;
import com.fiap.tech.product.domain.ProductGateway;

import java.util.Objects;



public class DefaultCreateProductUseCase extends CreateProductUseCase {
    private final ProductGateway productGateway;
    private final CategoryGateway categoryGateway;

    public DefaultCreateProductUseCase(final ProductGateway productGateway, final CategoryGateway categoryGateway) {
        this.productGateway = Objects.requireNonNull(productGateway);
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public CreateProductOutput execute(final CreateProductCommand aCommand) {
        final var aName = aCommand.name();
        final var aPrice = aCommand.price();
        final var aDescription = aCommand.description();
        final var aCategoryID = aCommand.categoryID();

        final var notification = Notification.create();

        notification.append(validateCategory(aCategoryID));

        final var aProduct = notification.validate(() -> Product.newProduct(aName, aDescription, aPrice));

        if (notification.hasErrors()) {
            throw new NotificationException("Could not create Aggregate Product", notification);
        }

        aProduct.setCategory(CategoryID.from(aCategoryID));

        return CreateProductOutput.from(this.productGateway.create(aProduct));
    }

    private ValidationHandler validateCategory(final String aCategoryID) {
        final var notification = Notification.create();

        if (aCategoryID == null || aCategoryID.isBlank()){
            return notification.append(new Error("Not allowed create a product without a category"));
        }

        final var hasCategory = categoryGateway.existsByIds(CategoryID.from(aCategoryID));

        if(!hasCategory){
            notification.append(new Error("Category with id %s was not found".formatted(aCategoryID)));
        }
        return notification;
    }

}
