package com.fiap.tech.product.infra.api.presenters;

import com.fiap.tech.product.application.retrieve.get.ProductOutput;
import com.fiap.tech.product.application.retrieve.list.ProductListOutput;
import com.fiap.tech.product.infra.models.ProductResponse;

public interface ProductApiPresenter  {

    static ProductResponse present(final ProductOutput output) {
        return new ProductResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.price(),
                output.categoryId()
        );
    }

    static ProductResponse present(final ProductListOutput output) {
        return new ProductResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.price(),
                output.categoryId()
        );
    }
}
