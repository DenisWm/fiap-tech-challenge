package com.fiap.tech.product.application.update;

import com.fiap.tech.categories.domain.CategoryGateway;
import com.fiap.tech.client.application.UseCaseTest;
import com.fiap.tech.common.domain.exceptions.NotFoundException;
import com.fiap.tech.common.domain.exceptions.NotificationException;
import com.fiap.tech.product.application.create.CreateProductCommand;
import com.fiap.tech.product.application.create.DefaultCreateProductUseCase;
import com.fiap.tech.product.domain.Product;
import com.fiap.tech.product.domain.ProductGateway;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpdateProductUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultUpdateProductUseCase useCase;

    @Mock
    private ProductGateway productGateway;

    @Mock
    private CategoryGateway categoryGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(productGateway, categoryGateway);
    }

    @Test
    void givenValidParams_whenCallsUpdate_shouldReturnId() {
        final var expectedName = "Lanche";
        final var expectedDescription = "description";
        final var expectedPrice = BigDecimal.valueOf(1.0);
        final var expectedCategoryId = "123";
        final var aProduct = Product.newProduct("name", "asd", BigDecimal.TEN);
        final var expectedId = aProduct.getId();
        when(productGateway.findById(any())).thenReturn(Optional.of(Product.with(aProduct)));
        when(productGateway.update(any())).thenAnswer(returnsFirstArg());

        when(categoryGateway.existsByIds(any())).thenReturn(true);

        final var aCmd = UpdateProductCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedPrice,
                expectedCategoryId
        );

        final var aResult = useCase.execute(aCmd);

        assertNotNull(aResult);
        assertNotNull(aResult.id());

        verify(productGateway).update(argThat(
                arg ->  Objects.equals(arg.getId(), expectedId)
                        && Objects.equals(arg.getName(), expectedName)
                        && Objects.equals(arg.getDescription(), expectedDescription)
                        && Objects.equals(arg.getPrice(), expectedPrice)
                        && Objects.equals(arg.getCategory().getValue(), expectedCategoryId)
        ));
    }

    @Test
    void givenNullCategoryId_whenCallsUpdate_shouldReturnNotification() {
        final var expectedName = "Lanche";
        final var expectedDescription = "description";
        final var expectedPrice = BigDecimal.valueOf(1.0);
        final String expectedCategoryId = null;

        final var aProduct = Product.newProduct("name", "asd", BigDecimal.TEN);

        final var expectedId = aProduct.getId();

        final var expectedErrorMessage = "Not allowed create a product without a category";
        final var expectedErrorCount = 1;

        when(productGateway.findById(any())).thenReturn(Optional.of(Product.with(aProduct)));

        final var aCmd = UpdateProductCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedPrice,
                expectedCategoryId
        );

        final var aResult = assertThrows(NotificationException.class, () -> useCase.execute(aCmd));

        assertNotNull(aResult);
        assertEquals(expectedErrorMessage, aResult.getErrors().get(0).message());
        assertEquals(expectedErrorCount, aResult.getErrors().size());
    }

    @Test
    void givenAnInexistentCategoryId_whenCallsUpdate_shouldReturnNotification() {
        final var expectedName = "Lanche";
        final var expectedDescription = "description";
        final var expectedPrice = BigDecimal.valueOf(1.0);
        final String expectedCategoryId = "123";

        final var aProduct = Product.newProduct("name", "asd", BigDecimal.TEN);

        final var expectedId = aProduct.getId();

        final var expectedErrorMessage = "Category with id 123 was not found";
        final var expectedErrorCount = 1;

        when(productGateway.findById(any())).thenReturn(Optional.of(Product.with(aProduct)));
        when(categoryGateway.existsByIds(any())).thenReturn(false);

        final var aCmd = UpdateProductCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedPrice,
                expectedCategoryId
        );

        final var aResult = assertThrows(NotificationException.class, () -> useCase.execute(aCmd));

        assertNotNull(aResult);
        assertEquals(expectedErrorMessage, aResult.getErrors().get(0).message());
        assertEquals(expectedErrorCount, aResult.getErrors().size());
    }

    @Test
    void givenAnInexistentProductId_whenCallsUpdate_shouldReturnId() {
        final var expectedName = "Lanche";
        final var expectedDescription = "description";
        final var expectedPrice = BigDecimal.valueOf(1.0);
        final String expectedCategoryId = "123";

        final var expectedId = "123";

        final var expectedErrorMessage = "Product with ID 123 was not found";

        when(productGateway.findById(any())).thenReturn(Optional.empty());

        final var aCmd = UpdateProductCommand.with(
                expectedId,
                expectedName,
                expectedDescription,
                expectedPrice,
                expectedCategoryId
        );

        final var aResult = assertThrows(NotFoundException.class, () -> useCase.execute(aCmd));

        assertNotNull(aResult);
        assertEquals(expectedErrorMessage, aResult.getMessage());
    }
}