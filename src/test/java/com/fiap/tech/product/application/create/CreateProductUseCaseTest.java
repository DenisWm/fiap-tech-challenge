package com.fiap.tech.product.application.create;

import com.fiap.tech.categories.domain.CategoryGateway;
import com.fiap.tech.client.application.UseCaseTest;
import com.fiap.tech.common.domain.exceptions.NotificationException;
import com.fiap.tech.product.domain.ProductGateway;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateProductUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCreateProductUseCase useCase;

    @Mock
    private ProductGateway productGateway;

    @Mock
    private CategoryGateway categoryGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(categoryGateway, productGateway);
    }

    @Test
    void givenValidCmd_whenCallsCreate_shouldReturnId() {
        final var expectedName = "Lanche";
        final var expectedDescription = "description";
        final var expectedPrice = BigDecimal.valueOf(1.0);
        final var expectedCategoryId = "123";

        when(productGateway.create(any())).thenAnswer(returnsFirstArg());
        when(categoryGateway.existsByIds(any())).thenReturn(true);

        final var aCmd = CreateProductCommand.with(expectedName, expectedDescription, expectedPrice, expectedCategoryId);

        final var aResult = useCase.execute(aCmd);

        assertNotNull(aResult);
        assertNotNull(aResult.id());

        verify(productGateway).create(argThat(
                arg -> Objects.equals(arg.getName(), expectedName)
                        && Objects.equals(arg.getDescription(), expectedDescription)
                        && Objects.equals(arg.getPrice(), expectedPrice)
                        && Objects.equals(arg.getCategory().getValue(), expectedCategoryId)
        ));
    }

    @Test
    void givenANullCategoryId_whenCallsCreate_shouldReturnNotification() {
        final var expectedName = "Lanche";
        final var expectedDescription = "description";
        final var expectedPrice = BigDecimal.valueOf(1.0);
        final var expectedErrorMessage = "Not allowed create a product without a category";
        final var expectedErrorCount = 1;

        final var aCmd = CreateProductCommand.with(expectedName, expectedDescription, expectedPrice, null);

        final var aResult = assertThrows(NotificationException.class, () -> useCase.execute(aCmd));


        assertNotNull(aResult);
        assertEquals(expectedErrorMessage, aResult.getErrors().get(0).message());
        assertEquals(expectedErrorCount, aResult.getErrors().size());
    }

    @Test
    void givenAnInexistentCategoryId_whenCallsCreate_shouldReturnNotification() {
        final var expectedName = "Lanche";
        final var expectedDescription = "description";
        final var expectedPrice = BigDecimal.valueOf(1.0);
        final var expectedErrorMessage = "Category with id 123 was not found";
        final var expectedErrorCount = 1;

        final var aCmd = CreateProductCommand.with(expectedName, expectedDescription, expectedPrice, "123");

        when(categoryGateway.existsByIds(any())).thenReturn(false);

        final var aResult = assertThrows(NotificationException.class, () -> useCase.execute(aCmd));

        assertNotNull(aResult);
        assertEquals(expectedErrorMessage, aResult.getErrors().get(0).message());
        assertEquals(expectedErrorCount, aResult.getErrors().size());
    }
}