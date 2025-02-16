package com.fiap.tech.client.infra.persistence;

import com.fiap.tech.IntegrationTest;
import com.fiap.tech.client.domain.Client;
import com.fiap.tech.client.domain.ClientGateway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
class ClientPostgresGatewayTest {

    @Autowired
    private ClientGateway clientGateway;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void testInjection() {
        assertNotNull(clientGateway);
        assertNotNull(clientRepository);
    }

    @Test
    void givenAValidClient_whenCallsCreate_shouldReturnClient() {
        final var expectedName = "Denis";
        final var expectedEmail = "denis@gmail.com";
        final var expectedCpf = "01205138200";

        final var aClient = Client.newClient(expectedName, expectedEmail, expectedCpf);

        assertEquals(0, clientRepository.count());

        final var aResult = clientGateway.create(aClient);

        assertEquals(1, clientRepository.count());

        assertEquals(expectedName, aResult.getName());
        assertEquals(expectedEmail, aResult.getEmail());
        assertEquals(expectedCpf, aResult.getCpf());

        final var actualClient = clientRepository.findById(aResult.getId().getValue()).get();

        assertEquals(expectedName, actualClient.getName());
        assertEquals(expectedEmail, actualClient.getEmail());
        assertEquals(expectedCpf, actualClient.getCpf());
    }

    @Test
    void givenAValidClient_whenCallsFindByCpf_shouldReturnClient() {
        final var expectedName = "Denis";
        final var expectedEmail = "denis@gmail.com";
        final var expectedCpf = "01205138200";

        final var aClient = Client.newClient(expectedName, expectedEmail, expectedCpf);

        assertEquals(0, clientRepository.count());

        clientRepository.saveAndFlush(ClientJpaEntity.from(aClient));

        assertEquals(1, clientRepository.count());

        final var aResult = clientGateway.findByCpf(expectedCpf).get();

        assertEquals(1, clientRepository.count());

        assertEquals(expectedName, aResult.getName());
        assertEquals(expectedEmail, aResult.getEmail());
        assertEquals(expectedCpf, aResult.getCpf());
    }

    @Test
    void givenAValidClient_whenCallsFindById_shouldReturnClient() {
        final var expectedName = "Denis";
        final var expectedEmail = "denis@gmail.com";
        final var expectedCpf = "01205138200";

        final var aClient = Client.newClient(expectedName, expectedEmail, expectedCpf);

        final var expectedId = aClient.getId();

        assertEquals(0, clientRepository.count());

        clientRepository.saveAndFlush(ClientJpaEntity.from(aClient));

        assertEquals(1, clientRepository.count());

        final var aResult = clientGateway.findById(expectedId).get();

        assertEquals(1, clientRepository.count());

        assertEquals(expectedName, aResult.getName());
        assertEquals(expectedEmail, aResult.getEmail());
        assertEquals(expectedCpf, aResult.getCpf());
    }

    @Test
    void givenAValidClient_whenCallsExistsById_shouldReturnTrue() {
        final var expectedName = "Denis";
        final var expectedEmail = "denis@gmail.com";
        final var expectedCpf = "01205138200";

        final var aClient = Client.newClient(expectedName, expectedEmail, expectedCpf);

        final var expectedId = aClient.getId();

        assertEquals(0, clientRepository.count());

        clientRepository.saveAndFlush(ClientJpaEntity.from(aClient));

        assertEquals(1, clientRepository.count());

        final var aResult = clientGateway.existsByID(expectedId.getValue());

        assertEquals(1, clientRepository.count());
        assertTrue(aResult);

        final var actualClient = clientRepository.findById(expectedId.getValue()).get();

        assertEquals(expectedName, actualClient.getName());
        assertEquals(expectedEmail, actualClient.getEmail());
        assertEquals(expectedCpf, actualClient.getCpf());
    }

    @Test
    void givenAValidClient_whenCallsExistsByCpf_shouldReturnTrue() {
        final var expectedName = "Denis";
        final var expectedEmail = "denis@gmail.com";
        final var expectedCpf = "01205138200";

        final var aClient = Client.newClient(expectedName, expectedEmail, expectedCpf);

        final var expectedId = aClient.getId();

        assertEquals(0, clientRepository.count());

        clientRepository.saveAndFlush(ClientJpaEntity.from(aClient));

        assertEquals(1, clientRepository.count());

        final var aResult = clientGateway.existsByCpf(expectedCpf);

        assertEquals(1, clientRepository.count());
        assertTrue(aResult);

        final var actualClient = clientRepository.findById(expectedId.getValue()).get();

        assertEquals(expectedName, actualClient.getName());
        assertEquals(expectedEmail, actualClient.getEmail());
        assertEquals(expectedCpf, actualClient.getCpf());
    }

    @Test
    void givenAValidClient_whenCallsExistsByEmail_shouldReturnTrue() {
        final var expectedName = "Denis";
        final var expectedEmail = "denis@gmail.com";
        final var expectedCpf = "01205138200";

        final var aClient = Client.newClient(expectedName, expectedEmail, expectedCpf);

        final var expectedId = aClient.getId();

        assertEquals(0, clientRepository.count());

        clientRepository.saveAndFlush(ClientJpaEntity.from(aClient));

        assertEquals(1, clientRepository.count());

        final var aResult = clientGateway.existsByEmail(expectedEmail);

        assertEquals(1, clientRepository.count());
        assertTrue(aResult);

        final var actualClient = clientRepository.findById(expectedId.getValue()).get();

        assertEquals(expectedName, actualClient.getName());
        assertEquals(expectedEmail, actualClient.getEmail());
        assertEquals(expectedCpf, actualClient.getCpf());
    }
}
