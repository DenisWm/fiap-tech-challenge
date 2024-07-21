package com.fiap.tech.fiap_tech_challenge.client.infra.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ClientResponse(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("email") String email,
        @JsonProperty("cpf") String cpf
) {
}
