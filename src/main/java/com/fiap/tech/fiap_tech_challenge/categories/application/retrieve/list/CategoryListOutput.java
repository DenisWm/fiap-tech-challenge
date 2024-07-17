package com.fiap.tech.fiap_tech_challenge.categories.application.retrieve.list;

import com.fiap.tech.fiap_tech_challenge.categories.domain.Category;
import com.fiap.tech.fiap_tech_challenge.categories.domain.CategoryID;
import com.fiap.tech.fiap_tech_challenge.product.application.retrieve.list.ProductListOutput;
import com.fiap.tech.fiap_tech_challenge.product.domain.Product;
import com.fiap.tech.fiap_tech_challenge.product.domain.ProductID;

import java.math.BigDecimal;

public record CategoryListOutput(
        CategoryID id,
        String name,
        String description
) {
    public static CategoryListOutput from(final Category category) {
        return new CategoryListOutput(category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}
