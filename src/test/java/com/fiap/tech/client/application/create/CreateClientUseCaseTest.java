package com.fiap.tech.client.application.create;

import com.fiap.tech.client.application.UseCaseTest;
import com.fiap.tech.client.domain.Client;
import com.fiap.tech.client.domain.ClientGateway;
import com.fiap.tech.common.domain.exceptions.NotificationException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateClientUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCreateClientUseCase useCase;

    @Mock
    private ClientGateway clientGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(clientGateway);
    }

    @Test
    void givenValidCommand_whenCallsCreate_shouldReturnId() {
        final var expectedName = "Denis";
        final var expectedEmail = "denis@gmail.com";
        final var expectedCpf = "01205138200";

        final var aCmd = CreateClientCommand.with(expectedName, expectedEmail, expectedCpf);

        when(clientGateway.create(any())).thenAnswer(returnsFirstArg());

        final var aResult = useCase.execute(aCmd);

        assertNotNull(aResult);
        assertNotNull(aResult.id());

        final var captor = ArgumentCaptor.forClass(Client.class);

        verify(clientGateway).create(captor.capture());

        final var aClient = captor.getValue();

        assertEquals(aResult.id(), aClient.getId().getValue());
        assertEquals(expectedName, aClient.getName());
        assertEquals(expectedEmail, aClient.getEmail());
        assertEquals(expectedCpf, aClient.getCpf());
    }

    @Test
    void givenInvalidCmdWithNullEmail_whenCallsCreate_shouldReturnException() {
        final var expectedName = "Denis";
        final String expectedEmail = null;
        final var expectedCpf = "01205138200";
        final var expectedErrorMessage = "'email' should not be null";
        final var expectedErrorCount = 1;
        final var expectedExceptionMessage = "Could not create Aggregate Client";

        final var aCmd = CreateClientCommand.with(expectedName, expectedEmail, expectedCpf);


        final var aResult = assertThrows(NotificationException.class, () -> useCase.execute(aCmd));

        assertNotNull(aResult);
        assertEquals(expectedExceptionMessage, aResult.getMessage());
        assertEquals(expectedErrorMessage, aResult.getErrors().get(0).message());
        assertEquals(expectedErrorCount, aResult.getErrors().size());

        verify(clientGateway, times(0)).create(any());
    }

    @Test
    void givenInvalidCmdWithEmptyEmail_whenCallsCreate_shouldReturnException() {
        final var expectedName = "Denis";
        final var expectedEmail = "";
        final var expectedCpf = "01205138200";
        final var expectedErrorMessage = "'email' should not be empty";
        final var expectedErrorCount = 1;
        final var expectedExceptionMessage = "Could not create Aggregate Client";

        final var aCmd = CreateClientCommand.with(expectedName, expectedEmail, expectedCpf);


        final var aResult = assertThrows(NotificationException.class, () -> useCase.execute(aCmd));

        assertNotNull(aResult);
        assertEquals(expectedExceptionMessage, aResult.getMessage());
        assertEquals(expectedErrorMessage, aResult.getErrors().get(0).message());
        assertEquals(expectedErrorCount, aResult.getErrors().size());

        verify(clientGateway, times(0)).create(any());
    }

    @Test
    void givenValidCmdWithExistentEmail_whenCallsCreate_shouldReturnException() {
        final var expectedName = "Denis";
        final var expectedEmail = "denis@gmail.com";
        final var expectedCpf = "01205138200";
        final var expectedErrorMessage = "'email' already exists";
        final var expectedErrorCount = 1;
        final var expectedExceptionMessage = "Could not create Aggregate Client";

        final var aCmd = CreateClientCommand.with(expectedName, expectedEmail, expectedCpf);

        when(clientGateway.existsByEmail(any())).thenReturn(true);

        final var aResult = assertThrows(NotificationException.class, () -> useCase.execute(aCmd));

        assertNotNull(aResult);
        assertEquals(expectedExceptionMessage, aResult.getMessage());
        assertEquals(expectedErrorMessage, aResult.getErrors().get(0).message());
        assertEquals(expectedErrorCount, aResult.getErrors().size());

        verify(clientGateway, times(0)).create(any());
    }

    @Test
    void givenAnInvalidCmdWithInvalidEmail_whenCallsCreate_shouldReturnException() {
        final var expectedName = "Denis";
        final var expectedEmail = "denisgmailcom";
        final var expectedCpf = "01205138200";
        final var expectedErrorMessage = "'email' should not be invalid";
        final var expectedErrorCount = 1;
        final var expectedExceptionMessage = "Could not create Aggregate Client";

        final var aCmd = CreateClientCommand.with(expectedName, expectedEmail, expectedCpf);

        final var aResult = assertThrows(NotificationException.class, () -> useCase.execute(aCmd));

        assertNotNull(aResult);
        assertEquals(expectedExceptionMessage, aResult.getMessage());
        assertEquals(expectedErrorMessage, aResult.getErrors().get(0).message());
        assertEquals(expectedErrorCount, aResult.getErrors().size());

        verify(clientGateway, times(0)).create(any());
    }

    @Test
    void givenInvalidCmdWithNullCpf_whenCallsCreate_shouldReturnException() {
        final var expectedName = "Denis";
        final var expectedEmail = "denis@gmail.com";
        final String expectedCpf = null;
        final var expectedErrorMessage = "'cpf' should not be null";
        final var expectedErrorCount = 1;
        final var expectedExceptionMessage = "Could not create Aggregate Client";

        final var aCmd = CreateClientCommand.with(expectedName, expectedEmail, expectedCpf);

        final var aResult = assertThrows(NotificationException.class, () -> useCase.execute(aCmd));

        assertNotNull(aResult);
        assertEquals(expectedExceptionMessage, aResult.getMessage());
        assertEquals(expectedErrorMessage, aResult.getErrors().get(0).message());
        assertEquals(expectedErrorCount, aResult.getErrors().size());

        verify(clientGateway, times(0)).create(any());
    }

    @Test
    void givenInvalidCmdWithEmptyCpf_whenCallsCreate_shouldReturnException() {
        final var expectedName = "Denis";
        final var expectedEmail = "denis@gmail.com";
        final var expectedCpf = "";
        final var expectedErrorMessage = "'cpf' should not be empty";
        final var expectedErrorCount = 1;
        final var expectedExceptionMessage = "Could not create Aggregate Client";

        final var aCmd = CreateClientCommand.with(expectedName, expectedEmail, expectedCpf);

        final var aResult = assertThrows(NotificationException.class, () -> useCase.execute(aCmd));

        assertNotNull(aResult);
        assertEquals(expectedExceptionMessage, aResult.getMessage());
        assertEquals(expectedErrorMessage, aResult.getErrors().get(0).message());
        assertEquals(expectedErrorCount, aResult.getErrors().size());

        verify(clientGateway, times(0)).create(any());
    }

    @Test
    void givenAValidCmdWithExistentCpf_whenCallsCreate_shouldReturnException() {
        final var expectedName = "Denis";
        final var expectedEmail = "denis@gmail.com";
        final var expectedCpf = "01205138200";
        final var expectedErrorMessage = "'cpf' already exists";
        final var expectedErrorCount = 1;
        final var expectedExceptionMessage = "Could not create Aggregate Client";

        final var aCmd = CreateClientCommand.with(expectedName, expectedEmail, expectedCpf);

        when(clientGateway.existsByCpf(any())).thenReturn(true);

        final var aResult = assertThrows(NotificationException.class, () -> useCase.execute(aCmd));

        assertNotNull(aResult);
        assertEquals(expectedExceptionMessage, aResult.getMessage());
        assertEquals(expectedErrorMessage, aResult.getErrors().get(0).message());
        assertEquals(expectedErrorCount, aResult.getErrors().size());

        verify(clientGateway, times(0)).create(any());
    }

    @Test
    void givenAnInvalidCmdWithInvalidCpf_whenCallsCreate_shouldReturnException() {
        final var expectedName = "Denis";
        final var expectedEmail = "denis@gmail.com";
        final var expectedCpf = "01205138201";
        final var expectedErrorMessage = "'cpf' should not be invalid";
        final var expectedErrorCount = 1;
        final var expectedExceptionMessage = "Could not create Aggregate Client";

        final var aCmd = CreateClientCommand.with(expectedName, expectedEmail, expectedCpf);

        final var aResult = assertThrows(NotificationException.class, () -> useCase.execute(aCmd));

        assertNotNull(aResult);
        assertEquals(expectedExceptionMessage, aResult.getMessage());
        assertEquals(expectedErrorMessage, aResult.getErrors().get(0).message());
        assertEquals(expectedErrorCount, aResult.getErrors().size());

        verify(clientGateway, times(0)).create(any());
    }
}