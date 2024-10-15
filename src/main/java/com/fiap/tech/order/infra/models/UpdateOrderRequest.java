package com.fiap.tech.order.infra.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record UpdateOrderRequest (
        @Schema(description = "The status of the order",
                allowableValues = {"in preparation", "ready", "completed"})
        @JsonProperty("status") String status
) {
}
