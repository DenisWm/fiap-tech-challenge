package com.fiap.tech.product.infra.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record UpdateProductRequest(
        @JsonProperty("productId") String name,
        @JsonProperty("description") String description,
        @JsonProperty("subTotal") BigDecimal price,
        @JsonProperty("category_id") String categoryId
) {
}
