package com.fiap.tech.categories.infra.api.presenters;

import com.fiap.tech.categories.infra.models.CategoryResponse;
import com.fiap.tech.categories.application.retrieve.get.CategoryOutput;
import com.fiap.tech.categories.application.retrieve.list.CategoryListOutput;

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
