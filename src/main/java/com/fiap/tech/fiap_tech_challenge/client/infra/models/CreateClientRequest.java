package com.fiap.tech.fiap_tech_challenge.client.infra.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record CreateClientRequest(
        @JsonProperty("name") String name,
        @JsonProperty("email") String email,
        @JsonProperty("cpf") String cpf
) {
}
