package com.fiap.tech.fiap_tech_challenge.common.infra.configuration.usecases;

import com.fiap.tech.fiap_tech_challenge.categories.application.retrieve.get.DefaultGetCategoryByIDUseCase;
import com.fiap.tech.fiap_tech_challenge.categories.application.retrieve.get.GetCategoryByIDUseCase;
import com.fiap.tech.fiap_tech_challenge.categories.application.retrieve.list.DefaultListCategoryUseCase;
import com.fiap.tech.fiap_tech_challenge.categories.application.retrieve.list.ListCategoryUseCase;
import com.fiap.tech.fiap_tech_challenge.categories.domain.CategoryGateway;
import com.fiap.tech.fiap_tech_challenge.product.application.create.CreateProductUseCase;
import com.fiap.tech.fiap_tech_challenge.product.application.create.DefaultCreateProductUseCase;
import com.fiap.tech.fiap_tech_challenge.product.application.delete.DefaultDeleteProductUseCase;
import com.fiap.tech.fiap_tech_challenge.product.application.delete.DeleteProductUseCase;
import com.fiap.tech.fiap_tech_challenge.product.application.retrieve.get.DefaultGetProductByIdUseCase;
import com.fiap.tech.fiap_tech_challenge.product.application.retrieve.get.GetProductByIdUseCase;
import com.fiap.tech.fiap_tech_challenge.product.application.retrieve.list.DefaultListProductsUseCase;
import com.fiap.tech.fiap_tech_challenge.product.application.retrieve.list.ListProductsUseCase;
import com.fiap.tech.fiap_tech_challenge.product.application.update.DefaultUpdateProductUseCase;
import com.fiap.tech.fiap_tech_challenge.product.application.update.UpdateProductUseCase;
import com.fiap.tech.fiap_tech_challenge.product.domain.ProductGateway;
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
