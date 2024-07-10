package com.fiap.tech.fiap_tech_challenge.product.infra.api.controllers;

import com.fiap.tech.fiap_tech_challenge.common.domain.pagination.Pagination;
import com.fiap.tech.fiap_tech_challenge.common.domain.pagination.SearchQuery;
import com.fiap.tech.fiap_tech_challenge.product.application.create.CreateProductCommand;
import com.fiap.tech.fiap_tech_challenge.product.application.create.CreateProductOutput;
import com.fiap.tech.fiap_tech_challenge.product.application.create.CreateProductUseCase;
import com.fiap.tech.fiap_tech_challenge.product.application.delete.DeleteProductUseCase;
import com.fiap.tech.fiap_tech_challenge.product.application.retrieve.get.GetProductByIdUseCase;
import com.fiap.tech.fiap_tech_challenge.product.application.retrieve.list.ListProductsUseCase;
import com.fiap.tech.fiap_tech_challenge.product.application.update.UpdateProductCommand;
import com.fiap.tech.fiap_tech_challenge.product.application.update.UpdateProductOutput;
import com.fiap.tech.fiap_tech_challenge.product.application.update.UpdateProductUseCase;
import com.fiap.tech.fiap_tech_challenge.product.infra.api.ProductAPI;
import com.fiap.tech.fiap_tech_challenge.product.infra.api.presenters.ProductApiPresenter;
import com.fiap.tech.fiap_tech_challenge.product.infra.models.CreateProductRequest;
import com.fiap.tech.fiap_tech_challenge.product.infra.models.ProductResponse;
import com.fiap.tech.fiap_tech_challenge.product.infra.models.UpdateProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class ProductController implements ProductAPI {

    private final CreateProductUseCase createProductUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final ListProductsUseCase listProductsUseCase;

    public ProductController(
            final CreateProductUseCase createProductUseCase,
            final GetProductByIdUseCase getProductByIdUseCase,
            final UpdateProductUseCase updateProductUseCase,
            final DeleteProductUseCase deleteProductUseCase,
            final ListProductsUseCase listProductsUseCase
    ) {
        this.createProductUseCase = Objects.requireNonNull(createProductUseCase);
        this.getProductByIdUseCase = Objects.requireNonNull(getProductByIdUseCase);
        this.updateProductUseCase = Objects.requireNonNull(updateProductUseCase);
        this.deleteProductUseCase = Objects.requireNonNull(deleteProductUseCase);
        this.listProductsUseCase = Objects.requireNonNull(listProductsUseCase);
    }

    @Override
    public ResponseEntity<?> createProduct(final CreateProductRequest input) {
        final var aCommand = CreateProductCommand.with(
                input.name(),
                input.description(),
                input.price(),
                input.categoryId()
        );

        final var output = this.createProductUseCase.execute(aCommand);

        return ResponseEntity.created(URI.create("/products/" + output.id())).body(output);
    }

    @Override
    public Pagination<ProductResponse> listProducts(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction) {

        final var aQuery = new SearchQuery(page, perPage, search, sort, direction);
        return listProductsUseCase.execute(aQuery).map(ProductApiPresenter::present);
    }

    @Override
    public ProductResponse getById(final String id) {
        return ProductApiPresenter.present(this.getProductByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateProductById(final String id, final UpdateProductRequest input) {
        final var aCommand = UpdateProductCommand.with(
                id,
                input.name(),
                input.description(),
                input.price(),
                input.categoryId()
        );

        final var output = this.updateProductUseCase.execute(aCommand);

        return ResponseEntity.ok(output);
    }

    @Override
    public void deleteProductById(final String id) {
        this.deleteProductUseCase.execute(id);
    }
}
