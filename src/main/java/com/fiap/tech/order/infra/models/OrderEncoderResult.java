package com.fiap.tech.order.infra.models;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.EXISTING_PROPERTY;

@JsonTypeInfo(use = NAME, include = EXISTING_PROPERTY, property = "status")
@OrderResponseTypes
public sealed interface OrderEncoderResult permits OrderEncoderCompleted, OrderEnconderErro{

    String getStatus();


}
