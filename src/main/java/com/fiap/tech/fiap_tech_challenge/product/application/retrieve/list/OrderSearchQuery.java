package com.fiap.tech.fiap_tech_challenge.product.application.retrieve.list;

public record OrderSearchQuery(
        int page,
        int perPage,
        String clientId,
        String sort,
        String direction
)
 {
}
