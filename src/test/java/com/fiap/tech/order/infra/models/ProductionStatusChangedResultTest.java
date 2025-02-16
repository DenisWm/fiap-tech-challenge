package com.fiap.tech.order.infra.models;

import com.fiap.tech.JacksonTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@JacksonTest
class ProductionStatusChangedResultTest {
    @Autowired
    private JacksonTester<ProductionStatusChangedResult> json;

    @Test
    public void testUnmarshall() throws Exception {
        final var expectedOrderId = "123";
        final var expectedStatus = "IN_PREPARATION";
        final var expectedStartedAt = Instant.now();
        final var expectedFinishedAt = Instant.now();

        final var json = """
                {
                  "order_id": "%s",
                  "status": "%s",
                  "started_at": "%s",
                  "finished_at": "%s"
                }  
                """.formatted(expectedOrderId, expectedStatus, expectedStartedAt, expectedFinishedAt);

        final var actualJson = this.json.parse(json);

        Assertions.assertThat(actualJson)
                .hasFieldOrPropertyWithValue("orderId", expectedOrderId)
                .hasFieldOrPropertyWithValue("status", expectedStatus)
                .hasFieldOrPropertyWithValue("startedAt", expectedStartedAt)
                .hasFieldOrPropertyWithValue("finishedAt", expectedFinishedAt)
        ;
    }
}