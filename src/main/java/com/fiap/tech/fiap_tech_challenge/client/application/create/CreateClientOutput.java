package com.fiap.tech.fiap_tech_challenge.client.application.create;


import com.fiap.tech.fiap_tech_challenge.client.domain.Client;

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
