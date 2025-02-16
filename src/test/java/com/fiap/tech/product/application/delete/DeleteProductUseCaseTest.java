package com.fiap.tech.product.application.delete;

import com.fiap.tech.client.application.UseCaseTest;
import com.fiap.tech.product.domain.ProductGateway;
import com.fiap.tech.product.domain.ProductID;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class DeleteProductUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultDeleteProductUseCase useCase;

    @Mock
    private ProductGateway productGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(productGateway);
    }

    @Test
    void givenValidId_whenCallsDelete_shouldBeOk() {
        final var expectedId = ProductID.unique();

        doNothing().when(productGateway).deleteById(any());

        useCase.execute(expectedId.getValue());
    }
}