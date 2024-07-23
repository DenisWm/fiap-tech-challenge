package com.fiap.tech.fiap_tech_challenge.categories.infra.api.presenters;

import com.fiap.tech.fiap_tech_challenge.categories.application.retrieve.get.CategoryOutput;
import com.fiap.tech.fiap_tech_challenge.categories.application.retrieve.list.CategoryListOutput;
import com.fiap.tech.fiap_tech_challenge.categories.infra.models.CategoryResponse;
import com.fiap.tech.fiap_tech_challenge.product.application.retrieve.get.ProductOutput;
import com.fiap.tech.fiap_tech_challenge.product.application.retrieve.list.ProductListOutput;
import com.fiap.tech.fiap_tech_challenge.product.infra.models.ProductResponse;

public interface CategoryApiPresenter {

    static CategoryResponse present(final CategoryListOutput output) {
        return new CategoryResponse(
                output.id().getValue(),
                output.name(),
                output.description()
        );
    }

    static CategoryResponse present(final CategoryOutput output) {
        return new CategoryResponse(
                output.id().getValue(),
                output.name(),
                output.description()
        );
    }
}
