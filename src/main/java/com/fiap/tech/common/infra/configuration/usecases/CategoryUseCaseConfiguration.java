package com.fiap.tech.common.infra.configuration.usecases;

import com.fiap.tech.categories.application.retrieve.get.DefaultGetCategoryByIDUseCase;
import com.fiap.tech.categories.application.retrieve.get.GetCategoryByIDUseCase;
import com.fiap.tech.categories.application.retrieve.list.DefaultListCategoryUseCase;
import com.fiap.tech.categories.application.retrieve.list.ListCategoryUseCase;
import com.fiap.tech.categories.domain.CategoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class CategoryUseCaseConfiguration {

    private final CategoryGateway categoryGateway;

    public CategoryUseCaseConfiguration(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Bean
    public GetCategoryByIDUseCase getCategoryByIdUseCase() {
        return new DefaultGetCategoryByIDUseCase(categoryGateway);
    }

    @Bean
    public ListCategoryUseCase listCategoryUseCase() {
        return new DefaultListCategoryUseCase(categoryGateway);
    }
}
