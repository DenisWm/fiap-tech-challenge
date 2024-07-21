package com.fiap.tech.fiap_tech_challenge.client.application.create;

import com.fiap.tech.fiap_tech_challenge.client.domain.Client;
import com.fiap.tech.fiap_tech_challenge.client.domain.ClientGateway;
import com.fiap.tech.fiap_tech_challenge.common.domain.exceptions.NotificationException;
import com.fiap.tech.fiap_tech_challenge.common.domain.validation.handler.Notification;

public class DefaultCreateClientUseCase extends CreateClientUseCase {
    private final ClientGateway clientGateway;

    public DefaultCreateClientUseCase(ClientGateway clientGateway) {
        this.clientGateway = clientGateway;
    }

    @Override
    public CreateClientOutput execute(CreateClientCommand command) {

        final var name = command.name();
        final var email = command.email();
        final var cpf = command.cpf();

        final var notification = Notification.create();

        final var client = notification.validate(() -> Client.newClient(name, email, cpf));

        if (notification.hasErrors()) {
            throw new NotificationException("Could not create Aggregate Client", notification);
        }

        return CreateClientOutput.from(this.clientGateway.create(client));
    }
}
