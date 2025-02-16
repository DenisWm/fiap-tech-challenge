package com.fiap.tech.client.application.retrieve.get;

import com.fiap.tech.client.application.UseCaseTest;
import com.fiap.tech.client.domain.Client;
import com.fiap.tech.client.domain.ClientGateway;
import com.fiap.tech.common.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetClientByCpfUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultGetClientByCpfUseCase useCase;

    @Mock
    private ClientGateway clientGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(clientGateway);
    }

    @Test
    void givenValidCpf_whenCallsGetByCpf_thenReturnClient() {
        final var expectedName = "Denis";
        final var expectedEmail = "denis@gmail.com";
        final var expectedCpf = "01205138200";

        final var aClient = Client.newClient(expectedName, expectedEmail, expectedCpf);

        final var expectedId = aClient.getId();

        when(clientGateway.findByCpf(any())).thenReturn(Optional.of(aClient));

        final var aResult = useCase.execute(expectedCpf);

        assertNotNull(aResult);
        assertEquals(expectedId.getValue(), aResult.id());
        assertEquals(expectedName, aResult.name());
        assertEquals(expectedEmail, aResult.email());
        assertEquals(expectedCpf, aResult.cpf());

        verify(clientGateway).findByCpf(eq(expectedCpf));
    }

    @Test
    void givenAnInvalidCpf_whenCallsGetByCpf_thenReturnClient() {
        final var expectedCpf = "123";
        final var expectedErrorMessage = "Client with CPF 123 was not found";
        when(clientGateway.findByCpf(any())).thenReturn(Optional.empty());

        final var aResult = assertThrows(NotFoundException.class, () -> useCase.execute(expectedCpf));

        assertNotNull(aResult);
        assertEquals(expectedErrorMessage, aResult.getMessage());

    }
}