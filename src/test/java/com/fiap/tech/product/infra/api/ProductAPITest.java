package com.fiap.tech.product.infra.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.tech.ControllerTest;
import com.fiap.tech.categories.domain.CategoryID;
import com.fiap.tech.product.application.create.CreateProductCommand;
import com.fiap.tech.product.application.create.CreateProductOutput;
import com.fiap.tech.product.application.create.DefaultCreateProductUseCase;
import com.fiap.tech.product.application.delete.DefaultDeleteProductUseCase;
import com.fiap.tech.product.application.retrieve.get.DefaultGetProductByIdUseCase;
import com.fiap.tech.product.application.retrieve.list.DefaultListProductsUseCase;
import com.fiap.tech.product.application.update.DefaultUpdateProductUseCase;
import com.fiap.tech.product.application.update.UpdateProductCommand;
import com.fiap.tech.product.application.update.UpdateProductOutput;
import com.fiap.tech.product.infra.models.CreateProductRequest;
import com.fiap.tech.product.infra.models.UpdateProductRequest;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest(controllers = ProductAPI.class)
class ProductAPITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DefaultCreateProductUseCase createProductUseCase;

    @MockBean
    private DefaultUpdateProductUseCase updateProductUseCase;

    @MockBean
    private DefaultDeleteProductUseCase deleteProductUseCase;

    @MockBean
    private DefaultGetProductByIdUseCase getProductByIdUseCase;

    @MockBean
    private DefaultListProductsUseCase listProductsUseCase;

    @Test
    void givenValidRequest_whenCreateProduct_thenReturnIdAndStatus201() throws Exception {
        final var expectedName = "Test Product Name";
        final var expectedDescription = "Test Product Description";
        final var expectedPrice = BigDecimal.TEN;
        final var expectedId = "123";
        final var aRequest = new CreateProductRequest(expectedName, expectedDescription, expectedPrice, CategoryID.unique().getValue());

        when(createProductUseCase.execute(any())).thenReturn(CreateProductOutput.from(expectedId));

        final var request = post("/products")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(aRequest));

        final var response = mvc.perform(request).andDo(print());

        response.andExpect(status().isCreated())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON.toString()))
                .andExpect(header().string("Location", String.format("/products/%s", expectedId)))
                .andExpect(jsonPath("$.id", equalTo(expectedId)));

        final var captor = ArgumentCaptor.forClass(CreateProductCommand.class);

        verify(createProductUseCase).execute(captor.capture());

        final var aCmd = captor.getValue();

        assertEquals(expectedName, aCmd.name());
        assertEquals(expectedDescription, aCmd.description());
        assertEquals(expectedPrice, aCmd.price());
    }

    @Test
    void givenValidRequest_whenUpdateProduct_thenReturnIdAndStatus200() throws Exception {
        final var expectedName = "Test Product Name";
        final var expectedDescription = "Test Product Description";
        final var expectedPrice = BigDecimal.TEN;
        final var expectedId = "123";
        final var aRequest = new UpdateProductRequest(expectedName, expectedDescription, expectedPrice, CategoryID.unique().getValue());

        when(updateProductUseCase.execute(any())).thenReturn(UpdateProductOutput.from(expectedId));

        final var request = put("/products/{id}", expectedId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(aRequest));

        final var response = mvc.perform(request).andDo(print());

        response.andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$.id", equalTo(expectedId)));

        final var captor = ArgumentCaptor.forClass(UpdateProductCommand.class);

        verify(updateProductUseCase).execute(captor.capture());

        final var aCmd = captor.getValue();

        assertEquals(expectedName, aCmd.name());
        assertEquals(expectedDescription, aCmd.description());
        assertEquals(expectedPrice, aCmd.price());
    }
}