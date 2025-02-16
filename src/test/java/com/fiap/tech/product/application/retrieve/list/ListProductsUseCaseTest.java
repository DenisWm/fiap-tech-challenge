package com.fiap.tech.product.application.retrieve.list;

import com.fiap.tech.categories.domain.Category;
import com.fiap.tech.client.application.UseCaseTest;
import com.fiap.tech.common.domain.pagination.Pagination;
import com.fiap.tech.common.domain.pagination.SearchQuery;
import com.fiap.tech.product.domain.Product;
import com.fiap.tech.product.domain.ProductGateway;
import com.fiap.tech.product.domain.pagination.ProductSearchQuery;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ListProductsUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultListProductsUseCase useCase;

    @Mock
    private ProductGateway productGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(productGateway);
    }

    @Test
    void givenEmptyList_whenListProductsUseCase_thenReturnEmptyList() {
        final var products = List.<Product>of();
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTotal = 0;
        final var expectedSort = "name";
        final var expectedDir = "asc";
        final var expectedCategoryId = "";
        final var expectedItems = List.of();

        final var expectedPagination =
                new Pagination<>(expectedPage, expectedPerPage, expectedTotal, products);

        final var aQuery = new ProductSearchQuery(expectedPage, expectedPerPage, expectedCategoryId, expectedSort, expectedDir);

        when(productGateway.findAll(any())).thenReturn(expectedPagination);

        final var aResult = useCase.execute(aQuery);

        assertEquals(expectedPage, aResult.currentPage());
        assertEquals(expectedPerPage, aResult.perPage());
        assertEquals(expectedTotal, aResult.total());
        assertEquals(expectedItems, aResult.items());

        verify(productGateway).findAll(eq(aQuery));
    }
}