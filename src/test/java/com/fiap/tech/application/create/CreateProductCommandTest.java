package com.fiap.tech.application.create;

import com.fiap.tech.product.application.create.CreateProductCommand;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class CreateProductCommandTest {

    @Test
    public void testValidCreateProduct() {
        // Dados de teste válidos
        String name = "Produto A";
        String description = "Descrição do Produto A";
        BigDecimal price = new BigDecimal("99.99");
        String categoryID = "12345";

        // Criação do comando
        CreateProductCommand command = CreateProductCommand.with(name, description, price, categoryID);

        // Verificações
        assertNotNull(command);
        assertEquals(name, command.name());
        assertEquals(description, command.description());
        assertEquals(price, command.price());
        assertEquals(categoryID, command.categoryID());
    }
}
