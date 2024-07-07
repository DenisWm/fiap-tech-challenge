package com.fiap.tech.fiap_tech_challenge.product.infra;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<ProductJpaEntity, Long> {
}
