package com.fiap.tech.order.infra.models;

import com.fiap.tech.JacksonTest;
import com.fiap.tech.order.application.retrieve.list.OrderListOutput;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JacksonTest
class ListOrderResponseTest {
    @Autowired
    private JacksonTester<ListOrderResponse> json;

    @Test
    public void testMarshall() throws Exception {
        final var expectedId = "123";
        final var expectedTimestamp = Instant.now();
        final var expectedClient = "123";
        final var expectedTotal = BigDecimal.TEN;
        final var expectedStatus = "IN_PREPARATION";

        final var response = ListOrderResponse.from(new OrderListOutput(
                expectedId,
                expectedTimestamp,
                expectedClient,
                expectedTotal,
                expectedStatus)

        );
        final var actualJson = json.write(response);
        Assertions.assertThat(actualJson)
                .hasJsonPathValue("$.id", expectedId)
                .hasJsonPathValue("$.timestamp", expectedTimestamp)
                .hasJsonPathValue("$.client_id", expectedClient)
                .hasJsonPathValue("$.total", expectedClient)
                .hasJsonPathValue("$.status", expectedTotal)
        ;
    }
}