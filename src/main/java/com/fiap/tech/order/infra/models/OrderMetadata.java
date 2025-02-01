package com.fiap.tech.order.infra.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OrderMetadata(
        @JsonProperty("encoded_order_folder") String encodedOrderFolder,
        @JsonProperty("resource_id") String resourceId,
        @JsonProperty("file_Path") String filePath
        ){
    }
