package com.fiap.tech.fiap_tech_challenge.client.application.create;

import com.fiap.tech.fiap_tech_challenge.product.application.create.CreateProductCommand;

import java.math.BigDecimal;

public record CreateClientCommand(

        String name,
        String email,
        String cpf

) {

    public static CreateClientCommand with(final String name, final String email, final String cpf) {
        return new CreateClientCommand(name, email, cpf);
    }

}
