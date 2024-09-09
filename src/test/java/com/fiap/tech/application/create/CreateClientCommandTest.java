package com.fiap.tech.application.create;

import com.fiap.tech.client.application.create.CreateClientCommand;
import com.fiap.tech.client.domain.ClientGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CreateClientCommandTest {

    private ClientGateway clientGateway;

    @BeforeEach
    public void setUp() {
        clientGateway = Mockito.mock(ClientGateway.class);
    }

    @Test
    public void testValidCPFCreateClient() {
        // Dados de teste com CPF válido
        String name = "João Silva";
        String email = "joao.silva@example.com";
        String cpf = "123.456.789-00";

        // Configuração do mock para simular CPF válido
        when(clientGateway.existsByCpf(anyString())).thenReturn(false);

        // Criação do comando
        CreateClientCommand command = CreateClientCommand.with(name, email, cpf);

        // Verificações
        assertNotNull(command);
        assertEquals(name, command.name());
        assertEquals(email, command.email());
        assertEquals(cpf, command.cpf());
    }
}
