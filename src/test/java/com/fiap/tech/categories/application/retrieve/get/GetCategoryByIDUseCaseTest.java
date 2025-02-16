package com.fiap.tech.categories.application.retrieve.get;

import com.fiap.tech.categories.domain.Category;
import com.fiap.tech.categories.domain.CategoryGateway;
import com.fiap.tech.categories.domain.CategoryID;
import com.fiap.tech.client.application.UseCaseTest;
import com.fiap.tech.common.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class GetCategoryByIDUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultGetCategoryByIDUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(categoryGateway);
    }

    @Test
    void givenValidId_whenCallsGetCategoryById_thenReturnCategory() {
        final var expectedId = CategoryID.unique();
        final var expectedName = "Lanches";
        final var expectedDescription = "Acredite ou não, são lanches.";

        final var aCategory = Category.with(expectedId, expectedName, expectedDescription);

        when(categoryGateway.findById(any()))
                .thenReturn(Optional.of(Category.with(aCategory)));

        final var aResult = useCase.execute(expectedId.getValue());

        assertNotNull(aResult);

        assertEquals(expectedId, aResult.id());
        assertEquals(expectedName, aResult.name());
        assertEquals(expectedDescription, aResult.description());
    }

    @Test
    void givenInvalidId_whenCallsGetCategoryById_thenReturnExpection() {
        final var expectedId = CategoryID.from("invalid");
        final var expectedErrorMessage = "Category with ID invalid was not found";

        when(categoryGateway.findById(any()))
                .thenReturn(Optional.empty());

        final var aResult = assertThrows(NotFoundException.class, () -> useCase.execute(expectedId.getValue()));

        assertNotNull(aResult);

        assertEquals(expectedErrorMessage, aResult.getMessage());
    }
}