package com.fiap.tech.client.infra.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ClientResponse(
        @JsonProperty("id") String id,
        @JsonProperty("productId") String name,
        @JsonProperty("email") String email,
        @JsonProperty("cpf") String cpf
) {
}
