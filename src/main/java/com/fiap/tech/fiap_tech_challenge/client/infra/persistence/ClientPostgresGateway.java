package com.fiap.tech.fiap_tech_challenge.client.infra.persistence;

import com.fiap.tech.fiap_tech_challenge.client.domain.Client;
import com.fiap.tech.fiap_tech_challenge.client.domain.ClientGateway;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ClientPostgresGateway implements ClientGateway {
    private final ClientRepository clientRepository;

    public ClientPostgresGateway(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Optional<Client> findByCpf(String cpf) {
        return this.clientRepository.findByCpf(cpf).map(ClientJpaEntity::toAggregate);
    }

    @Override
    public Client create(Client client) {
        return this.clientRepository.save(ClientJpaEntity.from(client)).toAggregate();
    }

    @Override
    public boolean existsByEmail(String email) {
        return clientRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return clientRepository.existsByCpf(cpf);
    }
}
