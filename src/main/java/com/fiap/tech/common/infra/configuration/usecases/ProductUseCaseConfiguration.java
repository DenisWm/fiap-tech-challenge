package com.fiap.tech.common.infra.configuration.usecases;

import com.fiap.tech.categories.domain.CategoryGateway;
import com.fiap.tech.product.application.create.CreateProductUseCase;
import com.fiap.tech.product.application.create.DefaultCreateProductUseCase;
import com.fiap.tech.product.application.delete.DefaultDeleteProductUseCase;
import com.fiap.tech.product.application.delete.DeleteProductUseCase;
import com.fiap.tech.product.application.retrieve.get.DefaultGetProductByIdUseCase;
import com.fiap.tech.product.application.retrieve.get.GetProductByIdUseCase;
import com.fiap.tech.product.application.retrieve.list.DefaultListProductsUseCase;
import com.fiap.tech.product.application.retrieve.list.ListProductsUseCase;
import com.fiap.tech.product.application.update.DefaultUpdateProductUseCase;
import com.fiap.tech.product.application.update.UpdateProductUseCase;
import com.fiap.tech.product.domain.ProductGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class ProductUseCaseConfiguration {

    private final ProductGateway productGateway;
    private final CategoryGateway categoryGateway;

    public ProductUseCaseConfiguration(final ProductGateway productGateway, final CategoryGateway categoryGateway) {
        this.productGateway = Objects.requireNonNull(productGateway);
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Bean
    public CreateProductUseCase createProductUseCase() {
        return new DefaultCreateProductUseCase(productGateway, categoryGateway);
    }

    @Bean
    public UpdateProductUseCase updateProductUseCase() {
        return new DefaultUpdateProductUseCase(productGateway, categoryGateway);
    }

    @Bean
    public DeleteProductUseCase deleteProductUseCase() {
        return new DefaultDeleteProductUseCase(productGateway);
    }

    @Bean
    public GetProductByIdUseCase getProductByIdUseCase() {
        return new DefaultGetProductByIdUseCase(productGateway);
    }

    @Bean
    public ListProductsUseCase listProductUseCase() {
        return new DefaultListProductsUseCase(productGateway);
    }
}
