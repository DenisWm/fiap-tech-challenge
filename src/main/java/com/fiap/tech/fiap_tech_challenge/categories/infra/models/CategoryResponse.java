package com.fiap.tech.fiap_tech_challenge.categories.infra.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record CategoryResponse(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("description") String description
) {
}
