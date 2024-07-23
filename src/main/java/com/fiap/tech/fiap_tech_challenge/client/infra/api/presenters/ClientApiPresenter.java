package com.fiap.tech.fiap_tech_challenge.client.infra.api.presenters;

import com.fiap.tech.fiap_tech_challenge.client.application.retrieve.get.ClientOutput;
import com.fiap.tech.fiap_tech_challenge.client.infra.models.ClientResponse;
import com.fiap.tech.fiap_tech_challenge.product.application.retrieve.get.ProductOutput;
import com.fiap.tech.fiap_tech_challenge.product.application.retrieve.list.ProductListOutput;
import com.fiap.tech.fiap_tech_challenge.product.infra.models.ProductResponse;

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
