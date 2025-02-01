package com.fiap.tech.order.infra.models;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.annotation.JsonSubTypes;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSubTypes({
        @JsonSubTypes.Type(value = OrderEncoderCompleted.class),
        @JsonSubTypes.Type(value = OrderEncoderErro.class),
})
public @interface OrderResponseTypes {

}
