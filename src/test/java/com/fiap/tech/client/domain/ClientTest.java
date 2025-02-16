package com.fiap.tech.client.domain;

import com.fiap.tech.common.domain.exceptions.NotificationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void givenValidParams_whenCreateClient_thenReturnClient() {
        final var expectedName = "Denis";
        final var expectedEmail = "denis@gmail.com";
        final var expectedCpf = "01205138200";

        final var aClient = Client.newClient(expectedName, expectedEmail, expectedCpf);

        assertNotNull(aClient);
        assertEquals(expectedName, aClient.getName());
        assertEquals(expectedEmail, aClient.getEmail());
        assertEquals(expectedCpf, aClient.getCpf());
    }

    @Test
    void givenEmptyName_whenCreateClient_thenReturnException() {
        final var expectedName = "";
        final var expectedEmail = "denis@gmail.com";
        final var expectedCpf = "01205138200";
        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedErrorCount = 1;

        final var aError = assertThrows(NotificationException.class, () -> Client.newClient(expectedName, expectedEmail, expectedCpf));

        assertNotNull(aError);
        assertEquals(expectedErrorMessage, aError.getErrors().get(0).message());
        assertEquals(expectedErrorCount, aError.getErrors().size());
    }

    @Test
    void givenNullName_whenCreateClient_thenReturnException() {
        final String expectedName = null;
        final var expectedEmail = "denis@gmail.com";
        final var expectedCpf = "01205138200";
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aError = assertThrows(NotificationException.class, () -> Client.newClient(expectedName, expectedEmail, expectedCpf));

        assertNotNull(aError);
        assertEquals(expectedErrorMessage, aError.getErrors().get(0).message());
        assertEquals(expectedErrorCount, aError.getErrors().size());
    }

    @Test
    void givenNameWithMoreThan255Chars_whenCreateClient_thenReturnException() {
        final var expectedName = """
                DenisDenisDenisDenisDenisDenisDenisDenisDenisDenisDenisDenisDenisDenisDenisDenisDenisDenisDenisDenis
                DenisDenisDenisDenisDenisDenisDenisDenisDenisDenisDenisDenisDenisDenisDenisDenisDenisDenisDenisDenis
                DenisDenisDenisDenisDenisDenisDenisDenisDenisDenisDenisDenisDenisDenisDenisDenisDenisDenisDenisDenis
                Denis
                """;
        final var expectedEmail = "denis@gmail.com";
        final var expectedCpf = "01205138200";
        final var expectedErrorMessage = "'name' must be between 1 and 255 characters";
        final var expectedErrorCount = 1;

        final var aError = assertThrows(NotificationException.class, () -> Client.newClient(expectedName, expectedEmail, expectedCpf));

        assertNotNull(aError);
        assertEquals(expectedErrorMessage, aError.getErrors().get(0).message());
        assertEquals(expectedErrorCount, aError.getErrors().size());
    }

    @Test
    void givenValidParams_whenUpdateClient_thenReturnClient() {
        final var expectedName = "Denis";
        final var expectedEmail = "denis@gmail.com";
        final var expectedCpf = "01205138200";

        final var aClient = Client.newClient("asd", "asd", "asd");

        final var updatedClient = Client.with(aClient.update(expectedName, expectedEmail, expectedCpf));

        assertNotNull(aClient);
        assertEquals(expectedName, updatedClient.getName());
        assertEquals(expectedEmail, updatedClient.getEmail());
        assertEquals(expectedCpf, updatedClient.getCpf());
    }

}