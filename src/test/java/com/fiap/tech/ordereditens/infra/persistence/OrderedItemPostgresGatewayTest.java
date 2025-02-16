package com.fiap.tech.ordereditens.infra.persistence;

import com.fiap.tech.IntegrationTest;
import com.fiap.tech.categories.domain.Category;
import com.fiap.tech.categories.domain.CategoryID;
import com.fiap.tech.categories.infra.CategoryJpaEntity;
import com.fiap.tech.categories.infra.CategoryRepository;
import com.fiap.tech.ordereditens.domain.OrderedItem;
import com.fiap.tech.product.domain.Product;
import com.fiap.tech.product.domain.ProductID;
import com.fiap.tech.product.infra.persistense.ProductJpaEntity;
import com.fiap.tech.product.infra.persistense.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
class OrderedItemPostgresGatewayTest {

    @Autowired
    private OrderedItemPostgresGateway orderedItemGateway;
    @Autowired
    private OrderedItemRepository orderedItemRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testInjection() {
        assertNotNull(orderedItemGateway);
        assertNotNull(orderedItemRepository);
        assertNotNull(productRepository);
        assertNotNull(categoryRepository);
    }

    @Test
    void givenOrderedItem_whenCallsCreate_shouldReturnOrderedItem() {
        final var expectedQuantity = 5;
        final var expectedPrice = BigDecimal.TEN;
        final var category = Category.with(CategoryID.unique(), "name", "description");
        final var product = Product.newProduct("name", "descript", expectedPrice);
        product.setCategory(category.getId());
        categoryRepository.saveAndFlush(CategoryJpaEntity.from(category));

        productRepository.saveAndFlush(ProductJpaEntity.from(product));

        final var orderedItem = OrderedItem.newOrderedItem(product.getId(), expectedQuantity, expectedPrice);

        assertEquals(0, orderedItemRepository.count());

        final var aResult = orderedItemGateway.create(orderedItem);

        assertEquals(product.getId(), aResult.getProduct());
        assertEquals(expectedQuantity, aResult.getQuantity());
        assertEquals(expectedPrice, aResult.getPrice());
    }

}