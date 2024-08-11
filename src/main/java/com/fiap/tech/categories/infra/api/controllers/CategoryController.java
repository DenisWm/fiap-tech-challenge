package com.fiap.tech.categories.infra.api.controllers;

import com.fiap.tech.categories.infra.models.CategoryResponse;
import com.fiap.tech.categories.application.retrieve.get.GetCategoryByIDUseCase;
import com.fiap.tech.categories.application.retrieve.list.ListCategoryUseCase;
import com.fiap.tech.categories.infra.api.CategoryAPI;
import com.fiap.tech.categories.infra.api.presenters.CategoryApiPresenter;
import com.fiap.tech.common.domain.pagination.Pagination;
import com.fiap.tech.common.domain.pagination.SearchQuery;
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
