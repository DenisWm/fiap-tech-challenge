package com.fiap.tech.order.infra.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OrderMessage(
        @JsonProperty("resource_id") String resourceId,
        @JsonProperty("file_Path") String filePath
) {
}
