package com.fiap.tech.fiap_tech_challenge.client.domain;

import com.fiap.tech.fiap_tech_challenge.common.domain.pagination.Pagination;
import com.fiap.tech.fiap_tech_challenge.product.domain.Product;
import com.fiap.tech.fiap_tech_challenge.product.domain.ProductID;
import com.fiap.tech.fiap_tech_challenge.product.domain.pagination.ProductSearchQuery;

import java.util.Optional;

public interface ClientGateway {

    Optional<Client> findByCpf(String cpf);
    Client create(Client client);
    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);
}
