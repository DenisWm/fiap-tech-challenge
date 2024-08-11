package com.fiap.tech.client.infra.api.presenters;

import com.fiap.tech.client.application.retrieve.get.ClientOutput;
import com.fiap.tech.client.infra.models.ClientResponse;

public interface ClientApiPresenter {

    static ClientResponse present(final ClientOutput output) {
        return new ClientResponse(
                output.id(),
                output.name(),
                output.email(),
                output.cpf()
        );
    }
}
