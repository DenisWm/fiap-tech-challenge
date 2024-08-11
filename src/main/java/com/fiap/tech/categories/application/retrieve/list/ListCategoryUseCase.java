package com.fiap.tech.categories.application.retrieve.list;

import com.fiap.tech.common.application.UseCase;
import com.fiap.tech.common.domain.pagination.Pagination;
import com.fiap.tech.common.domain.pagination.SearchQuery;

public abstract class ListCategoryUseCase extends UseCase<SearchQuery, Pagination<CategoryListOutput>> {
}
