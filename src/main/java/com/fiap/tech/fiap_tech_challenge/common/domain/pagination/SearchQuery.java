package com.fiap.tech.fiap_tech_challenge.common.domain.pagination;

public record SearchQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction
) {
}
