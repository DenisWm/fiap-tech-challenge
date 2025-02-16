package com.fiap.tech.categories.infra;

import com.fiap.tech.IntegrationTest;
import com.fiap.tech.categories.domain.Category;
import com.fiap.tech.categories.domain.CategoryID;
import com.fiap.tech.common.domain.pagination.Pagination;
import com.fiap.tech.common.domain.pagination.SearchQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
class CategoryPostgresGatewayTest {

    @Autowired
    private CategoryPostgresGateway categoryGateway;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testInjection() {
        assertNotNull(categoryGateway);
        assertNotNull(categoryRepository);
    }

    @Test
    void givenCategory_whenFindById_thenShouldReturnCategory() {
        final var expectedId = CategoryID.unique();
        final var expectedName = "Lanches";
        final var expectedDescription = "Acredite ou não, são lanches.";

        final var aCategory = Category.with(expectedId, expectedName, expectedDescription);

        assertEquals(0, categoryRepository.count());

        categoryRepository.saveAndFlush(CategoryJpaEntity.from(aCategory));

        assertEquals(1, categoryRepository.count());

        final var aResult = categoryGateway.findById(expectedId).get();

        assertNotNull(aResult);
        assertEquals(expectedId, aResult.getId());
        assertEquals(expectedName, aResult.getName());
        assertEquals(expectedDescription, aResult.getDescription());

        final var actualCategory = categoryGateway.findById(aCategory.getId()).get();

        assertEquals(expectedId, actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
    }

    @Test
    void givenCategory_whenExistsById_thenShouldReturnTrue() {
        final var expectedId = CategoryID.unique();
        final var expectedName = "Lanches";
        final var expectedDescription = "Acredite ou não, são lanches.";

        final var aCategory = Category.with(expectedId, expectedName, expectedDescription);

        assertEquals(0, categoryRepository.count());

        categoryRepository.saveAndFlush(CategoryJpaEntity.from(aCategory));

        assertEquals(1, categoryRepository.count());

        final var aResult = categoryGateway.existsByIds(expectedId);

        assertNotNull(aResult);
        assertTrue(aResult);

        final var actualCategory = categoryGateway.findById(aCategory.getId()).get();

        assertEquals(expectedId, actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
    }

    @Test
    void givenCategories_whenFindAll_thenShouldReturnPaginating() {
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTotal = 1;
        final var expectedSort = "name";
        final var expectedDir = "asc";
        final var expectedTerms = "lanch";

        final var aQuery = new SearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDir);

        final var aCategory = Category.with(CategoryID.from("123"), "Lanche", "expectedDescription");

        assertEquals(0, categoryRepository.count());

        categoryRepository.saveAndFlush(CategoryJpaEntity.from(aCategory));

        assertEquals(1, categoryRepository.count());


        final var actualResult = categoryGateway.findAll(aQuery);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedTotal, actualResult.items().size());
    }
}