package com.fiap.tech.fiap_tech_challenge.product.domain.pagination;

public record ProductSearchQuery(
        int page,
        int perPage,
        String categoryId,
        String sort,
        String direction
) {
}
