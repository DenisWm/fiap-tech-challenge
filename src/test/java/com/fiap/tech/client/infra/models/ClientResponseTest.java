package com.fiap.tech.client.infra.models;

import com.fiap.tech.JacksonTest;
import com.fiap.tech.order.application.retrieve.get.OrderOutput;
import com.fiap.tech.order.domain.OrderStatus;
import com.fiap.tech.order.infra.models.OrderResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JacksonTest
class ClientResponseTest {

    @Autowired
    private JacksonTester<ClientResponse> json;

    @Test
    public void testMarshall() throws Exception {
        final var expectedId = "123";
        final var expectedName = "denis";
        final var expectedEmail = "denis@gmail.com";
        final var expectedCpf = "012305138200";


        final var response = new ClientResponse(expectedId, expectedName, expectedEmail, expectedCpf);
        final var actualJson = json.write(response);
        Assertions.assertThat(actualJson)
                .hasJsonPathValue("$.id", expectedId)
                .hasJsonPathValue("$.name", expectedName)
                .hasJsonPathValue("$.email", expectedEmail)
                .hasJsonPathValue("$.cpf", expectedCpf)
        ;
    }
}