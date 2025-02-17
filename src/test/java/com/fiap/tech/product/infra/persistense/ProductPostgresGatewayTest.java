package com.fiap.tech.product.infra.persistense;

import com.fiap.tech.IntegrationTest;
import com.fiap.tech.categories.domain.Category;
import com.fiap.tech.categories.domain.CategoryID;
import com.fiap.tech.categories.infra.CategoryJpaEntity;
import com.fiap.tech.categories.infra.CategoryRepository;
import com.fiap.tech.product.domain.Product;
import com.fiap.tech.product.domain.pagination.ProductSearchQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
class ProductPostgresGatewayTest {

    @Autowired
    private ProductPostgresGateway productGateway;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testInjection() {
        assertNotNull(productGateway);
        assertNotNull(productRepository);
        assertNotNull(categoryRepository);
    }

    @Test
    void givenProduct_whenCreateProduct_thenShouldReturnProduct() {
        final var aCategory = categoryRepository.saveAndFlush(
                CategoryJpaEntity.from(
                        new Category(CategoryID.unique(), "categoryName", "description"))).toAggregate();
        final var expectedName = "Lanche";
        final var expectedDescription = "description";
        final var expectedPrice = BigDecimal.valueOf(1.0);
        assertEquals(0, productRepository.count());

        final var aProduct = Product.newProduct(expectedName, expectedDescription, expectedPrice);
        aProduct.setCategory(aCategory.getId());

        final var aResult = productGateway.create(aProduct);

        assertEquals(1, productRepository.count());

        assertEquals(expectedName, aResult.getName());
        assertEquals(expectedDescription, aResult.getDescription());
        assertEquals(expectedPrice, aResult.getPrice());

        final var actualProduct = productRepository.findById(aProduct.getId().getValue()).get();

        assertEquals(expectedName, actualProduct.getName());
        assertEquals(expectedDescription, actualProduct.getDescription());
        assertEquals(expectedPrice.toBigInteger(), actualProduct.getPrice().toBigInteger());
    }

    @Test
    void givenProduct_whenUpdateProduct_thenShouldReturnProduct() {
        final var aCategory = categoryRepository.saveAndFlush(
                CategoryJpaEntity.from(
                        new Category(CategoryID.unique(), "categoryName", "description"))).toAggregate();
        final var expectedName = "Lanche";
        final var expectedDescription = "description";
        final var expectedPrice = BigDecimal.valueOf(1.0);
        assertEquals(0, productRepository.count());

        final var aProduct = Product.newProduct("asd", "asd", BigDecimal.TEN);
        aProduct.setCategory(aCategory.getId());
        productGateway.create(aProduct);

        aProduct.update(expectedName, expectedDescription, expectedPrice);

        assertEquals(1, productRepository.count());

        final var aResult = productGateway.update(aProduct);

        assertEquals(expectedName, aResult.getName());
        assertEquals(expectedDescription, aResult.getDescription());
        assertEquals(expectedPrice, aResult.getPrice());

        final var actualProduct = productRepository.findById(aProduct.getId().getValue()).get();

        assertEquals(expectedName, actualProduct.getName());
        assertEquals(expectedDescription, actualProduct.getDescription());
        assertEquals(expectedPrice.toBigInteger(), actualProduct.getPrice().toBigInteger());
    }

    @Test
    void givenProduct_whenExistsById_thenShouldTrue() {
        final var aCategory = categoryRepository.saveAndFlush(
                CategoryJpaEntity.from(
                        new Category(CategoryID.unique(), "categoryName", "description"))).toAggregate();
        final var expectedName = "Lanche";
        final var expectedDescription = "description";
        final var expectedPrice = BigDecimal.valueOf(1.0);
        assertEquals(0, productRepository.count());

        final var aProduct = Product.newProduct(expectedName, expectedDescription, expectedPrice);
        aProduct.setCategory(aCategory.getId());
        productGateway.create(aProduct);

        assertEquals(1, productRepository.count());

        final var aResult = productGateway.existsByIds(List.of(aProduct.getId().getValue()));

        assertEquals(aProduct.getId(), aResult.get(0));

        final var actualProduct = productRepository.findById(aProduct.getId().getValue()).get();

        assertEquals(expectedName, actualProduct.getName());
        assertEquals(expectedDescription, actualProduct.getDescription());
        assertEquals(expectedPrice.toBigInteger(), actualProduct.getPrice().toBigInteger());
    }

    @Test
    void givenProduct_whenFindById_thenShouldProduct() {
        final var aCategory = categoryRepository.saveAndFlush(
                CategoryJpaEntity.from(
                        new Category(CategoryID.unique(), "categoryName", "description"))).toAggregate();
        final var expectedName = "Lanche";
        final var expectedDescription = "description";
        final var expectedPrice = BigDecimal.valueOf(1.0);
        assertEquals(0, productRepository.count());

        final var aProduct = Product.newProduct(expectedName, expectedDescription, expectedPrice);
        aProduct.setCategory(aCategory.getId());
        productGateway.create(aProduct);

        assertEquals(1, productRepository.count());

        final var aResult = productGateway.findById(aProduct.getId()).get();

        assertEquals(expectedName, aResult.getName());
        assertEquals(expectedDescription, aResult.getDescription());
        assertEquals(expectedPrice.toBigInteger(), aResult.getPrice().toBigInteger());
    }

    @Test
    void givenProduct_whenFindByIds_thenShouldProduct() {
        final var aCategory = categoryRepository.saveAndFlush(
                CategoryJpaEntity.from(
                        new Category(CategoryID.unique(), "categoryName", "description"))).toAggregate();
        final var expectedName = "Lanche";
        final var expectedDescription = "description";
        final var expectedPrice = BigDecimal.valueOf(1.0);
        assertEquals(0, productRepository.count());

        final var aProduct = Product.newProduct(expectedName, expectedDescription, expectedPrice);
        aProduct.setCategory(aCategory.getId());
        productGateway.create(aProduct);

        assertEquals(1, productRepository.count());

        final var aResult = productGateway.findByIds(List.of(aProduct.getId().getValue())).get(0);

        assertEquals(expectedName, aResult.getName());
        assertEquals(expectedDescription, aResult.getDescription());
        assertEquals(expectedPrice.toBigInteger(), aResult.getPrice().toBigInteger());
    }

    @Test
    void givenProduct_whenFindAll_thenShouldProduct() {
        final var aCategory = categoryRepository.saveAndFlush(
                CategoryJpaEntity.from(
                        new Category(CategoryID.unique(), "categoryName", "description"))).toAggregate();
        assertEquals(0, productRepository.count());

        final var aProduct = Product.newProduct("Lanche", "description", BigDecimal.valueOf(1.0));
        aProduct.setCategory(aCategory.getId());

        productGateway.create(aProduct);

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTotal = 1;
        final var expectedCategoryId = aCategory.getId();
        final var expectedSort = "name";
        final var expectedDir = "asc";

        assertEquals(1, productRepository.count());

        final var aQuery = new ProductSearchQuery(expectedPage, expectedPerPage, expectedCategoryId.getValue(), expectedSort, expectedDir);

        final var aResult = productGateway.findAll(aQuery);

        assertEquals(expectedPage, aResult.currentPage());
        assertEquals(expectedPerPage, aResult.perPage());
        assertEquals(expectedTotal, aResult.total());
        assertEquals(expectedTotal, aResult.items().size());
    }


    @Test
    void givenProductWithBlankCategoryId_whenFindAll_thenShouldProduct() {
        final var aCategory = categoryRepository.saveAndFlush(
                CategoryJpaEntity.from(
                        new Category(CategoryID.unique(), "categoryName", "description"))).toAggregate();
        assertEquals(0, productRepository.count());

        final var aProduct = Product.newProduct("Lanche", "description", BigDecimal.valueOf(1.0));
        aProduct.setCategory(aCategory.getId());

        productGateway.create(aProduct);

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTotal = 1;
        final var expectedCategoryId = "";
        final var expectedSort = "name";
        final var expectedDir = "asc";

        assertEquals(1, productRepository.count());

        final var aQuery = new ProductSearchQuery(expectedPage, expectedPerPage, expectedCategoryId, expectedSort, expectedDir);

        final var aResult = productGateway.findAll(aQuery);

        assertEquals(expectedPage, aResult.currentPage());
        assertEquals(expectedPerPage, aResult.perPage());
        assertEquals(expectedTotal, aResult.total());
        assertEquals(expectedTotal, aResult.items().size());
    }

    @Test
    void givenProduct_whenDeleteById_thenShouldDelete() {
        final var aCategory = categoryRepository.saveAndFlush(
                CategoryJpaEntity.from(
                        new Category(CategoryID.unique(), "categoryName", "description"))).toAggregate();
        assertEquals(0, productRepository.count());

        final var aProduct = Product.newProduct("Lanche", "description", BigDecimal.valueOf(1.0));
        aProduct.setCategory(aCategory.getId());

        productGateway.create(aProduct);


        assertEquals(1, productRepository.count());

        productGateway.deleteById(aProduct.getId());

        assertEquals(0, productRepository.count());
    }
}