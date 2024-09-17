package com.fiap.tech.categories.infra.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record CategoryResponse(
        @JsonProperty("id") String id,
        @JsonProperty("productId") String name,
        @JsonProperty("description") String description
) {
}
