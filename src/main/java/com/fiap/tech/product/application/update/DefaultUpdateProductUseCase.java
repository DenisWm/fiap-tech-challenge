package com.fiap.tech.product.application.update;

import com.fiap.tech.categories.domain.CategoryGateway;
import com.fiap.tech.categories.domain.CategoryID;
import com.fiap.tech.common.domain.exceptions.NotificationException;
import com.fiap.tech.common.domain.validation.Error;
import com.fiap.tech.common.domain.validation.ValidationHandler;
import com.fiap.tech.common.domain.validation.handler.Notification;
import com.fiap.tech.product.domain.Product;
import com.fiap.tech.product.domain.ProductGateway;
import com.fiap.tech.product.domain.ProductID;

import java.util.Objects;

import static com.fiap.tech.common.application.utils.IDNotFoundUtils.notFound;


public class DefaultUpdateProductUseCase extends UpdateProductUseCase {
    private final ProductGateway productGateway;
    private final CategoryGateway categoryGateway;

    public DefaultUpdateProductUseCase(final ProductGateway productGateway, final CategoryGateway categoryGateway) {
        this.productGateway = Objects.requireNonNull(productGateway);
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public UpdateProductOutput execute(final UpdateProductCommand aCommand) {
        final var anId = ProductID.from(aCommand.id());
        final var aName = aCommand.name();
        final var aPrice = aCommand.price();
        final var aDescription = aCommand.description();
        final var aCategoryID = aCommand.categoryID();

        final var aProduct = productGateway.findById(anId).orElseThrow(notFound(anId, Product.class));

        final var notification = Notification.create();

        notification.append(validateCategory(aCategoryID));

        notification.validate(() -> aProduct.update(aName, aDescription, aPrice));

        if (notification.hasErrors()) {
            throw new NotificationException("Could not create Aggregate Product", notification);
        }

        aProduct.setCategory(CategoryID.from(aCategoryID));

        return UpdateProductOutput.from(this.productGateway.update(aProduct));
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
