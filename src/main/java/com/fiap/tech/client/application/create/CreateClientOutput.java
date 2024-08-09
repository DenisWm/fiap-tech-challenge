package com.fiap.tech.client.application.create;


import com.fiap.tech.client.domain.Client;

public record CreateClientOutput(
        String id
) {
    public static CreateClientOutput from (final Client client){
        return new CreateClientOutput(client.getId().getValue());
    }

    public static CreateClientOutput from (final String anId){
        return new CreateClientOutput(anId);
    }
}
