package com.fiap.tech.client.application.retrieve.get;

import com.fiap.tech.client.domain.Client;

public record ClientOutput(String id, String name, String email, String cpf) {

    public static ClientOutput from (Client client){
        return new ClientOutput(client.getId().getValue(), client.getName(), client.getEmail(), client.getCpf());
    }

}
