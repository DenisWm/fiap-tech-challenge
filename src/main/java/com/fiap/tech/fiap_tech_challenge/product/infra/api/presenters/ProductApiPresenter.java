package com.fiap.tech.fiap_tech_challenge.product.infra.api.presenters;

import com.fiap.tech.fiap_tech_challenge.product.application.retrieve.get.ProductOutput;
import com.fiap.tech.fiap_tech_challenge.product.application.retrieve.list.ProductListOutput;
import com.fiap.tech.fiap_tech_challenge.product.infra.models.ProductResponse;

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
