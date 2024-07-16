package com.fiap.tech.fiap_tech_challenge.categories.infra;


import com.fiap.tech.fiap_tech_challenge.categories.domain.Category;
import com.fiap.tech.fiap_tech_challenge.categories.domain.CategoryGateway;
import com.fiap.tech.fiap_tech_challenge.categories.domain.CategoryID;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CategoryPostgresGateway implements CategoryGateway {

    private final CategoryRepository categoryRepository;

    public CategoryPostgresGateway(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Optional<Category> findById(String id) {
        return Optional.empty();
    }

    @Override
    public boolean existsByIds(CategoryID id) {
        return categoryRepository.existsById(id.getValue());
    }
}
