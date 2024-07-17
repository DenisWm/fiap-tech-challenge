package com.fiap.tech.fiap_tech_challenge.categories.infra.api.controllers;

import com.fiap.tech.fiap_tech_challenge.categories.application.retrieve.get.GetCategoryByIDUseCase;
import com.fiap.tech.fiap_tech_challenge.categories.application.retrieve.list.ListCategoryUseCase;
import com.fiap.tech.fiap_tech_challenge.categories.infra.api.CategoryAPI;
import com.fiap.tech.fiap_tech_challenge.categories.infra.api.presenters.CategoryApiPresenter;
import com.fiap.tech.fiap_tech_challenge.categories.infra.models.CategoryResponse;
import com.fiap.tech.fiap_tech_challenge.common.domain.pagination.Pagination;
import com.fiap.tech.fiap_tech_challenge.common.domain.pagination.SearchQuery;
import com.fiap.tech.fiap_tech_challenge.product.infra.api.presenters.ProductApiPresenter;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController implements CategoryAPI {

    private final GetCategoryByIDUseCase getCategoryByIDUseCase;
    private final ListCategoryUseCase listCategoryUseCase;

    public CategoryController(GetCategoryByIDUseCase getCategoryByIDUseCase, ListCategoryUseCase listCategoryUseCase) {
        this.getCategoryByIDUseCase = getCategoryByIDUseCase;
        this.listCategoryUseCase = listCategoryUseCase;
    }


    @Override
    public Pagination<CategoryResponse> listCategories(String terms, int page, int perPage, String sort, String direction) {
        final var query = new SearchQuery(page, perPage, terms, sort, direction);
        return listCategoryUseCase.execute(query).map(CategoryApiPresenter::present);
    }

    @Override
    public CategoryResponse getById(String id) {
        return CategoryApiPresenter.present(this.getCategoryByIDUseCase.execute(id));
    }
}
