package com.fiap.tech.fiap_tech_challenge.client.infra.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record CreateClientRequest(
        @JsonProperty("name") String name,
        @JsonProperty("email") String email,
        @JsonProperty("cpf") String cpf
) {
}
