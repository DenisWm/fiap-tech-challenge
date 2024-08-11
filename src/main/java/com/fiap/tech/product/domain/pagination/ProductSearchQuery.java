package com.fiap.tech.product.domain.pagination;

public record ProductSearchQuery(
        int page,
        int perPage,
        String categoryId,
        String sort,
        String direction
) {
}
