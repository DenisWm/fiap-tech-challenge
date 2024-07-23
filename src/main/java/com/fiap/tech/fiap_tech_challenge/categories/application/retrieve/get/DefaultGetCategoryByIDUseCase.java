package com.fiap.tech.fiap_tech_challenge.categories.application.retrieve.get;

import com.fiap.tech.fiap_tech_challenge.categories.domain.Category;
import com.fiap.tech.fiap_tech_challenge.categories.domain.CategoryGateway;
import com.fiap.tech.fiap_tech_challenge.categories.domain.CategoryID;
import com.fiap.tech.fiap_tech_challenge.product.application.retrieve.get.ProductOutput;
import com.fiap.tech.fiap_tech_challenge.product.domain.Product;
import com.fiap.tech.fiap_tech_challenge.product.domain.ProductID;

import static com.fiap.tech.fiap_tech_challenge.common.application.utils.IDNotFoundUtils.notFound;

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
