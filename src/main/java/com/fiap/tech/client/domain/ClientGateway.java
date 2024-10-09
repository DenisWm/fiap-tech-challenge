package com.fiap.tech.client.domain;

import java.util.Optional;

public interface ClientGateway {

    Optional<Client> findByCpf(String cpf);
    Client create(Client client);
    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);

    boolean existsByID(String id);

    Optional<Client> findById(ClientID clientId);
}
