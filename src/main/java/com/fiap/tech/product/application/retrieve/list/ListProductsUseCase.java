package com.fiap.tech.product.application.retrieve.list;


import com.fiap.tech.common.application.UseCase;
import com.fiap.tech.common.domain.pagination.Pagination;
import com.fiap.tech.product.domain.pagination.ProductSearchQuery;

public abstract class ListProductsUseCase extends UseCase<ProductSearchQuery, Pagination<ProductListOutput>> {
}
