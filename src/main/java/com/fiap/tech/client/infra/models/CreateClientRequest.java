package com.fiap.tech.client.infra.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateClientRequest(
        @JsonProperty("name") String name,
        @JsonProperty("email") String email,
        @JsonProperty("cpf") String cpf
) {
}
