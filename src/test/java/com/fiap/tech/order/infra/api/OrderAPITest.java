package com.fiap.tech.order.infra.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.tech.ControllerTest;
import com.fiap.tech.client.domain.ClientID;
import com.fiap.tech.order.application.create.CreateOrderOutput;
import com.fiap.tech.order.application.create.DefaultCreateOrderUseCase;
import com.fiap.tech.order.application.retrieve.get.DefaultGetOrderByIdUseCase;
import com.fiap.tech.order.application.retrieve.list.DefaultListOrderUseCase;
import com.fiap.tech.order.domain.OrderID;
import com.fiap.tech.order.infra.models.CreateOrderRequest;
import com.fiap.tech.order.infra.models.ItemsRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest(controllers = OrderAPI.class)
class OrderAPITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DefaultCreateOrderUseCase createOrderUseCase;

    @MockBean
    private DefaultGetOrderByIdUseCase getOrderByIdUseCase;

    @MockBean
    private DefaultListOrderUseCase listOrderUseCase;

    @Test
    void givenValidRequest_whenCreateOrder_thenReturnIdAndStatus201() throws Exception {
        final var expectedClientId = ClientID.unique();
        final var expectedItem = ItemsRequest.from("productId", 5);
        final var expectedId = OrderID.from("orderId").getValue();
        final var request = new CreateOrderRequest(
                expectedClientId.getValue(),
                List.of(expectedItem)
        );

        when(createOrderUseCase.execute(any())).thenReturn(CreateOrderOutput.from(expectedId));

        final var aRequest = post("/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

        final var response = mvc.perform(aRequest).andDo(print());

        response.andExpect(status().isCreated())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON.toString()))
                .andExpect(header().string("Location", String.format("/orders/%s", expectedId)))
                .andExpect(jsonPath("$.id", equalTo(expectedId)));
    }

}