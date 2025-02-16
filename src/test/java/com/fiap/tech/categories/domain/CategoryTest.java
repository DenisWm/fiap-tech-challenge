package com.fiap.tech.categories.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void givenValidParams_whenCallsConstructor_shouldReturnCategory() {
        final var expectedId = CategoryID.unique();
        final var expectedName = "Lanches";
        final var expectedDescription = "Acredite ou não, são lanches.";

        final var aCategory = new Category(expectedId, expectedName, expectedDescription);

        assertNotNull(aCategory);
        assertEquals(expectedId, aCategory.getId());
        assertEquals(expectedName, aCategory.getName());
        assertEquals(expectedDescription, aCategory.getDescription());
    }

    @Test
    void givenValidParams_whenCallsWith_shouldReturnCategory() {
        final var expectedId = CategoryID.unique();
        final var expectedName = "Lanches";
        final var expectedDescription = "Acredite ou não, são lanches.";

        final var aCategory = Category.with(expectedId, expectedName, expectedDescription);

        assertNotNull(aCategory);
        assertEquals(expectedId, aCategory.getId());
        assertEquals(expectedName, aCategory.getName());
        assertEquals(expectedDescription, aCategory.getDescription());
    }

}