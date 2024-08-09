package com.fiap.tech.categories.application.retrieve.list;

import com.fiap.tech.categories.domain.Category;
import com.fiap.tech.categories.domain.CategoryID;

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
