package com.fiap.tech.client.domain;

import com.fiap.tech.common.domain.AggregateRoot;
import com.fiap.tech.common.domain.exceptions.NotificationException;
import com.fiap.tech.common.domain.validation.ValidationHandler;
import com.fiap.tech.common.domain.validation.handler.Notification;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Client extends AggregateRoot<ClientID> {

    private String name;
    private String email;
    private String cpf;

    private Client(ClientID clientID, String name, String email, String cpf) {
        super(clientID);
        this.name = name;
        this.email = email;
        this.cpf = cpf;

        selfValidate();
    }

    private void selfValidate() {
        final var notification = Notification.create();

        validate(notification);

        if(notification.hasErrors()) {
            throw new NotificationException("", notification);
        }
    }

    public static Client newClient (
            final String name,
            final String email,
            final String cpf
    ) {
        final var id = ClientID.unique();
        return new Client(id, name, email, cpf);
    }

    public static Client with(Client client){
        return with(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getCpf()
        );
    }

    public static Client with(
            final ClientID id,
            final String name,
            final String email,
            final String cpf
    ){
        return new Client(id, name, email, cpf);
    }

    public Client update(final String name, final String email, final String cpf){
        this.name = name;
        this.email = email;
        this.cpf = cpf;

        return this;

    }

    @Override
    public void validate(ValidationHandler handler) {
        new ClientValidator(handler,this).validate();
    }
}
