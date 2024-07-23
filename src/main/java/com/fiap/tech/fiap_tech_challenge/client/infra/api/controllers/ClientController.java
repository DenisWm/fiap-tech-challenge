package com.fiap.tech.fiap_tech_challenge.client.infra.api.controllers;

import com.fiap.tech.fiap_tech_challenge.client.application.create.CreateClientCommand;
import com.fiap.tech.fiap_tech_challenge.client.application.create.CreateClientUseCase;
import com.fiap.tech.fiap_tech_challenge.client.application.retrieve.get.GetClientByCpfUseCase;
import com.fiap.tech.fiap_tech_challenge.client.infra.api.ClientAPI;
import com.fiap.tech.fiap_tech_challenge.client.infra.api.presenters.ClientApiPresenter;
import com.fiap.tech.fiap_tech_challenge.client.infra.models.ClientResponse;
import com.fiap.tech.fiap_tech_challenge.client.infra.models.CreateClientRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class ClientController implements ClientAPI {
    private final CreateClientUseCase createClientUseCase;
    private final GetClientByCpfUseCase getClientByCpfUseCase;

    public ClientController(CreateClientUseCase createClientUseCase, GetClientByCpfUseCase getClientByCpfUseCase) {
        this.createClientUseCase = createClientUseCase;
        this.getClientByCpfUseCase = getClientByCpfUseCase;
    }

    @Override
    public ResponseEntity<?> createClient(CreateClientRequest input) {
        final var aCommand = CreateClientCommand.with(
                input.name(),
                input.email(),
                input.cpf()
        );
            final var output = this.createClientUseCase.execute(aCommand);
            return ResponseEntity.created(URI.create("/clients/" + output.id())).body(output);
    }

    @Override
    public ClientResponse getByCpf(String cpf) {
        return ClientApiPresenter.present(this.getClientByCpfUseCase.execute(cpf));
    }
}
