package com.fiap.tech.order.infra.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("ERROR")
public record OrderEnconderErro(
        @JsonProperty("message") OrderMessage message,
        @JsonProperty("error") String error
) implements OrderEncoderResult{

    private static final String ERROR = "ERROR";

    @Override
    public String getStatus(){
        return ERROR;
    }
}
