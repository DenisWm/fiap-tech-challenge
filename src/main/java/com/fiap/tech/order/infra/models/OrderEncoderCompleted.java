package com.fiap.tech.order.infra.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("COMPLETED")
public record OrderEncoderCompleted(
    @JsonProperty("id") String id,
    @JsonProperty("output_bucket_path") String outputBucket,
    @JsonProperty("order") OrderMetadata order
) implements OrderEncoderResult {

    private static final String COMPLETED = "COMPLETED";

    @Override
    public String getStatus(){
        return COMPLETED;
    }
}
