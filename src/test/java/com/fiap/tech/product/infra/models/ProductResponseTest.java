package com.fiap.tech.product.infra.models;

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
class ProductResponseTest {

    @Autowired
    private JacksonTester<ProductResponse> json;

    @Test
    public void testMarshall() throws Exception {
        final var expectedId = "123";
        final var expectedName = "productName";
        final var expectedDescription = "description";
        final var expectedSubTotal = BigDecimal.TEN;
        final var expectedCategoryId = "123";


        final var response = new ProductResponse(
                expectedId,
                expectedName,
                expectedDescription,
                expectedSubTotal,
                expectedCategoryId

        );
        final var actualJson = json.write(response);
        Assertions.assertThat(actualJson)
                .hasJsonPathValue("$.id", expectedId)
                .hasJsonPathValue("$.name", expectedName)
                .hasJsonPathValue("$.description", expectedDescription)
                .hasJsonPathValue("$.subTotal", expectedSubTotal)
                .hasJsonPathValue("$.category_id", expectedCategoryId)
        ;
    }
}