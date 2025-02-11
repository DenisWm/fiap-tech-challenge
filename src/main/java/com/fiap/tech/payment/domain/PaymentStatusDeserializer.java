package com.fiap.tech.payment.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class PaymentStatusDeserializer extends JsonDeserializer<PaymentStatus> {
    @Override
    public PaymentStatus deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        JsonNode valueNode = node.get("value");
        if (valueNode != null) {
            return PaymentStatus.valueOf(valueNode.asText());
        }
        throw new IOException("Invalid payment status format");
    }
}
