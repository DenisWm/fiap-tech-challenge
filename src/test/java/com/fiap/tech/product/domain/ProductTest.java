package com.fiap.tech.product.domain;

import com.fiap.tech.categories.domain.Category;
import com.fiap.tech.categories.domain.CategoryID;
import com.fiap.tech.common.domain.exceptions.NotificationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void givenValidParams_whenCallsCreateProduct_thenShouldReturnProduct() {
        final var expectedName = "Lanche";
        final var expectedDescription = "description";
        final var expectedPrice = BigDecimal.valueOf(1.0);

        final var aProduct = Product.newProduct(expectedName, expectedDescription, expectedPrice);

        assertEquals(expectedName, aProduct.getName());
        assertEquals(expectedDescription, aProduct.getDescription());
        assertEquals(expectedPrice, aProduct.getPrice());
    }

    @Test
    void givenNullName_whenCallsCreateProduct_thenShouldReturnException() {
        final String expectedName = null;
        final var expectedDescription = "description";
        final var expectedPrice = BigDecimal.valueOf(1.0);
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aResult = assertThrows(NotificationException.class, () -> Product.newProduct(expectedName, expectedDescription, expectedPrice));

        assertNotNull(aResult);
        assertEquals(expectedErrorMessage, aResult.getErrors().get(0).message());
        assertEquals(expectedErrorCount, aResult.getErrors().size());
    }

    @Test
    void givenEmptyName_whenCallsCreateProduct_thenShouldReturnException() {
        final var expectedName = "";
        final var expectedDescription = "description";
        final var expectedPrice = BigDecimal.valueOf(1.0);
        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedErrorCount = 1;

        final var aResult = assertThrows(NotificationException.class, () -> Product.newProduct(expectedName, expectedDescription, expectedPrice));

        assertNotNull(aResult);
        assertEquals(expectedErrorMessage, aResult.getErrors().get(0).message());
        assertEquals(expectedErrorCount, aResult.getErrors().size());
    }

    @Test
    void givenANameWithMoreThan255Chars_whenCallsCreateProduct_thenShouldReturnException() {
        final var expectedName = """
                Name Name Name Name Name Name Name Name Name Name Name Name Name Name Name Name Name Name Name Name Name
                Name Name Name Name Name Name Name Name Name Name Name Name Name Name Name Name Name Name Name Name Name
                Name Name Name Name Name Name Name Name Name Name Name Name Name Name Name Name Name Name Name Name Name
                """;
        final var expectedDescription = "description";
        final var expectedPrice = BigDecimal.valueOf(1.0);
        final var expectedErrorMessage = "'name' must be between 1 and 255 characters";
        final var expectedErrorCount = 1;

        final var aResult = assertThrows(NotificationException.class, () -> Product.newProduct(expectedName, expectedDescription, expectedPrice));

        assertNotNull(aResult);
        assertEquals(expectedErrorMessage, aResult.getErrors().get(0).message());
        assertEquals(expectedErrorCount, aResult.getErrors().size());
    }

    @Test
    void givenNullPrice_whenCallsCreateProduct_thenShouldReturnException() {
        final var expectedName = "Name";
        final var expectedDescription = "description";
        final BigDecimal expectedPrice = null;
        final var expectedErrorMessage = "'price' should not be null";
        final var expectedErrorCount = 1;

        final var aResult = assertThrows(NotificationException.class, () -> Product.newProduct(expectedName, expectedDescription, expectedPrice));

        assertNotNull(aResult);
        assertEquals(expectedErrorMessage, aResult.getErrors().get(0).message());
        assertEquals(expectedErrorCount, aResult.getErrors().size());
    }

    @Test
    void givenZeroPrice_whenCallsCreateProduct_thenShouldReturnException() {
        final var expectedName = "Name";
        final var expectedDescription = "description";
        final var expectedPrice = BigDecimal.ZERO;
        final var expectedErrorMessage = "'price' must be greater than 0";
        final var expectedErrorCount = 1;

        final var aResult = assertThrows(NotificationException.class, () -> Product.newProduct(expectedName, expectedDescription, expectedPrice));

        assertNotNull(aResult);
        assertEquals(expectedErrorMessage, aResult.getErrors().get(0).message());
        assertEquals(expectedErrorCount, aResult.getErrors().size());
    }

    @Test
    void givenValidParams_whenCallsUpdate_thenShouldReturnProduct() {
        final var expectedName = "Lanche";
        final var expectedDescription = "description";
        final var expectedPrice = BigDecimal.valueOf(1.0);

        final var aProduct = Product.newProduct("asd", "asd", BigDecimal.ONE);

        final var updatedProduct = Product.with(aProduct).update(expectedName, expectedDescription, expectedPrice);

        assertEquals(expectedName, updatedProduct.getName());
        assertEquals(expectedDescription, updatedProduct.getDescription());
        assertEquals(expectedPrice, updatedProduct.getPrice());
    }
}