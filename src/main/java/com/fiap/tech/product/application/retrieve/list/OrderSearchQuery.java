package com.fiap.tech.product.application.retrieve.list;

public record OrderSearchQuery(
        int page,
        int perPage,
        String clientId,
        String sort,
        String direction
)
 {
}
