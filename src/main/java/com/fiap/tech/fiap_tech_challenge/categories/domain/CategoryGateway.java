package com.fiap.tech.fiap_tech_challenge.categories.domain;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryGateway {

    Optional<Category> findById(String id);
}
