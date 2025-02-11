package com.fiap.tech.client.infra.persistence;

import com.fiap.tech.client.domain.Client;
import com.fiap.tech.client.domain.ClientID;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "clients")
@Getter
@Setter
public class ClientJpaEntity {
    @Id
    private String id;
    private String name;
    private String email;
    private String cpf;

    public ClientJpaEntity(String id, String name, String email, String cpf) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
    }

    public ClientJpaEntity() {
    }

    public static ClientJpaEntity from(final Client client) {
        return new ClientJpaEntity(client.getId().getValue(), client.getName(), client.getEmail(), client.getCpf());
    }

    public Client toAggregate() {
        return Client.with(ClientID.from(this.getId()), this.getName(), this.getEmail(), this.getCpf());

    }

}
