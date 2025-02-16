package com.fiap.tech.product.application.retrieve.get;

import com.fiap.tech.categories.domain.CategoryID;
import com.fiap.tech.client.application.UseCaseTest;
import com.fiap.tech.common.domain.exceptions.NotFoundException;
import com.fiap.tech.product.domain.Product;
import com.fiap.tech.product.domain.ProductGateway;
import com.fiap.tech.product.domain.ProductID;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class GetProductByIdUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultGetProductByIdUseCase useCase;

    @Mock
    private ProductGateway productGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(productGateway);
    }

    @Test
    void givenValidId_whenCallsGetById_thenReturnProduct() {
        final var expectedName = "Lanche";
        final var expectedDescription = "description";
        final var expectedPrice = BigDecimal.valueOf(1.0);

        final var aProduct = Product.newProduct(expectedName, expectedDescription, expectedPrice);
        aProduct.setCategory(CategoryID.unique());
        final var expectedId = aProduct.getId();

        when(productGateway.findById(any())).thenReturn(Optional.of(Product.with(aProduct)));

        final var aResult = useCase.execute(expectedId.getValue());

        assertNotNull(aResult);
        assertEquals(expectedName, aResult.name());
        assertEquals(expectedDescription, aResult.description());
        assertEquals(expectedPrice, aResult.price());
    }

    @Test
    void givenInvalidId_whenCallsGetById_thenReturnException() {
        final var expectedId = ProductID.from("invalid-id");
        final var expectedErrorMessage = "Product with ID invalid-id was not found";
        when(productGateway.findById(any())).thenReturn(Optional.empty());

        final var aResult = assertThrows(NotFoundException.class, () -> useCase.execute(expectedId.getValue()));

        assertNotNull(aResult);
        assertEquals(expectedErrorMessage, aResult.getMessage());
    }
}