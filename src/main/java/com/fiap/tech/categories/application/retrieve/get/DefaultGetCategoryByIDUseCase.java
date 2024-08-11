package com.fiap.tech.categories.application.retrieve.get;

import com.fiap.tech.categories.domain.Category;
import com.fiap.tech.categories.domain.CategoryGateway;
import com.fiap.tech.categories.domain.CategoryID;

import static com.fiap.tech.common.application.utils.IDNotFoundUtils.notFound;

public class DefaultGetCategoryByIDUseCase extends GetCategoryByIDUseCase{
    private final CategoryGateway categoryGateway;

    public DefaultGetCategoryByIDUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public CategoryOutput execute(String anId) {
        final var ID = CategoryID.from(anId);
        return categoryGateway.findById(ID)
                .map(CategoryOutput::from)
                .orElseThrow(notFound(ID, Category.class));
    }
}
