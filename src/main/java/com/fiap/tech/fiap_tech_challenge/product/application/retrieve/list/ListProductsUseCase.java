package com.fiap.tech.fiap_tech_challenge.product.application.retrieve.list;


import com.fiap.tech.fiap_tech_challenge.common.application.UseCase;
import com.fiap.tech.fiap_tech_challenge.common.domain.pagination.Pagination;
import com.fiap.tech.fiap_tech_challenge.common.domain.pagination.SearchQuery;

public abstract class ListProductsUseCase extends UseCase<SearchQuery, Pagination<ProductListOutput>> {
}
