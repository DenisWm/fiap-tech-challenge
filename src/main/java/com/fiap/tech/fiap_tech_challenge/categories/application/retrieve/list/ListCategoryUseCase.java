package com.fiap.tech.fiap_tech_challenge.categories.application.retrieve.list;

import com.fiap.tech.fiap_tech_challenge.common.application.UseCase;
import com.fiap.tech.fiap_tech_challenge.common.domain.pagination.Pagination;
import com.fiap.tech.fiap_tech_challenge.common.domain.pagination.SearchQuery;

public abstract class ListCategoryUseCase extends UseCase<SearchQuery, Pagination<CategoryListOutput>> {
}
