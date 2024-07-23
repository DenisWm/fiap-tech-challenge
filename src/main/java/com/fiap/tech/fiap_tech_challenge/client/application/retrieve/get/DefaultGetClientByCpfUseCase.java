package com.fiap.tech.fiap_tech_challenge.client.application.retrieve.get;

import com.fiap.tech.fiap_tech_challenge.client.domain.Client;
import com.fiap.tech.fiap_tech_challenge.client.domain.ClientGateway;

import static com.fiap.tech.fiap_tech_challenge.common.application.utils.CpfNotFoundUtils.notFound;

public class DefaultGetClientByCpfUseCase extends GetClientByCpfUseCase{

    private final ClientGateway clientGateway;

    public DefaultGetClientByCpfUseCase(ClientGateway clientGateway) {
        this.clientGateway = clientGateway;
    }

    @Override
    public ClientOutput execute(String cpf) {
        return clientGateway.findByCpf(cpf)
                .map(ClientOutput::from)
                .orElseThrow(notFound(cpf, Client.class));
    }
}
