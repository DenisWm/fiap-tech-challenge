package com.fiap.tech.client.application.retrieve.get;

import com.fiap.tech.common.application.utils.CpfNotFoundUtils;
import com.fiap.tech.client.domain.Client;
import com.fiap.tech.client.domain.ClientGateway;

public class DefaultGetClientByCpfUseCase extends GetClientByCpfUseCase{

    private final ClientGateway clientGateway;

    public DefaultGetClientByCpfUseCase(ClientGateway clientGateway) {
        this.clientGateway = clientGateway;
    }

    @Override
    public ClientOutput execute(String cpf) {
        return clientGateway.findByCpf(cpf)
                .map(ClientOutput::from)
                .orElseThrow(CpfNotFoundUtils.notFound(cpf, Client.class));
    }
}
