package com.fiap.tech.fiap_tech_challenge.categories.application.retrieve.get;

import com.fiap.tech.fiap_tech_challenge.categories.domain.Category;
import com.fiap.tech.fiap_tech_challenge.categories.domain.CategoryID;

public record CategoryOutput(
    CategoryID id,
    String name,
    String description
) {
        public static CategoryOutput from(final Category category) {
            return new CategoryOutput(
                    category.getId(),
                    category.getName(),
                    category.getDescription()
            );
        }
}
