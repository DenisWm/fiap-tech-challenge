package com.fiap.tech.categories.application.retrieve.list;

import com.fiap.tech.categories.domain.Category;
import com.fiap.tech.categories.domain.CategoryGateway;
import com.fiap.tech.client.application.UseCaseTest;
import com.fiap.tech.common.domain.pagination.Pagination;
import com.fiap.tech.common.domain.pagination.SearchQuery;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ListCategoryUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultListCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(categoryGateway);
    }

    @Test
    void givenEmptyResult_whenCallList_shouldReturnIt() {
        final var productions = List.<Category>of();
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTotal = 0;
        final var expectedSort = "name";
        final var expectedDir = "asc";
        final var expectedTerms = "";
        final var expectedItems = List.of();

        final var expectedPagination =
                new Pagination<>(expectedPage, expectedPerPage, expectedTotal, productions);

        final var aQuery = new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDir);

        when(categoryGateway.findAll(any())).thenReturn(expectedPagination);

        final var aResult = useCase.execute(aQuery);

        assertEquals(expectedPage, aResult.currentPage());
        assertEquals(expectedPerPage, aResult.perPage());
        assertEquals(expectedTotal, aResult.total());
        assertEquals(expectedItems, aResult.items());

        verify(categoryGateway).findAll(eq(aQuery));
    }
}