package com.fiap.tech.order.infra.models;

import com.fiap.tech.JacksonTest;
import com.fiap.tech.order.application.retrieve.get.OrderOutput;
import com.fiap.tech.order.domain.OrderStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JacksonTest
class OrderResponseTest {

    @Autowired
    private JacksonTester<OrderResponse> json;

    @Test
    public void testMarshall() throws Exception {
        final var expectedId = "123";
        final var expectedTimestamp = Instant.now();
        final var expectedClient = new OrderOutput.OrderedItemOutput.OrderedItemClientOutput("123", "name", "cpf");
        final var expectedTotal = BigDecimal.TEN;
        final var expectedStatus = OrderStatus.RECEIVED;
        final var expectedItem = new OrderOutput.OrderedItemOutput("123", 1, BigDecimal.TEN, new OrderOutput.OrderedItemOutput.OrderedItemProductOutput("123", "name", BigDecimal.TEN));
        final var expectedPaymentId = "123";

        final var response = OrderResponse.from(new OrderOutput(
                expectedId,
                expectedTimestamp,
                expectedTotal,
                List.of(expectedItem),
                expectedClient,
                expectedStatus,
                        expectedPaymentId)

        );
        final var actualJson = json.write(response);
        Assertions.assertThat(actualJson)
                .hasJsonPathValue("$.id", expectedId)
                .hasJsonPathValue("$.timestamp", expectedTimestamp)
                .hasJsonPathValue("$.client.id", expectedClient.id())
                .hasJsonPathValue("$.client.name", expectedClient.name())
                .hasJsonPathValue("$.client.cpf", expectedClient.cpf())
                .hasJsonPathValue("$.total", expectedTotal)
                .hasJsonPathValue("$.items[0].id", expectedItem.id())
                .hasJsonPathValue("$.items[0].quantity", expectedItem.quantity())
                .hasJsonPathValue("$.items[0].sub_total", expectedItem.subTotal())
                .hasJsonPathValue("$.items[0].product.id", expectedItem.product().id())
                .hasJsonPathValue("$.items[0].product.name", expectedItem.product().name())
                .hasJsonPathValue("$.items[0].product.price", expectedItem.product().price())
                .hasJsonPathValue("$.payment_id", expectedPaymentId)
        ;
    }


}