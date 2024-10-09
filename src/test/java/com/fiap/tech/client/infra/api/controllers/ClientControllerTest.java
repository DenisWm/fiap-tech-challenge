package com.fiap.tech.client.infra.api.controllers;

import com.fiap.tech.client.application.create.CreateClientOutput;
import com.fiap.tech.client.application.create.CreateClientUseCase;
import com.fiap.tech.client.application.retrieve.get.GetClientByCpfUseCase;
import com.fiap.tech.client.infra.models.CreateClientRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ClientControllerTest {

    @InjectMocks
    private ClientController clientController;

    @Mock
    private CreateClientUseCase createClientUseCase;

    @Mock
    private GetClientByCpfUseCase getClientByCpfUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateClient() {
        // Arrange
        CreateClientRequest request = new CreateClientRequest("Patricia Freitas", "patricia_freitas@example.com", "185.217.960-05");
        CreateClientOutput output = CreateClientOutput.from("1");

        // Mock the behavior of the use case
        when(createClientUseCase.execute(any())).thenReturn(output);

        // Act
        ResponseEntity<?> responseEntity = clientController.createClient(request);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(output, responseEntity.getBody());
    }
}
