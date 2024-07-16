package com.fiap.tech.fiap_tech_challenge.product.infra.persistense;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<ProductJpaEntity, String> {

    Page<ProductJpaEntity> findAll(Specification<ProductJpaEntity> whereClause, Pageable page);

}
