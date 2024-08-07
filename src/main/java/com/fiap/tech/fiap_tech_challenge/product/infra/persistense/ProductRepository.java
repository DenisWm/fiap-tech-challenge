package com.fiap.tech.fiap_tech_challenge.product.infra.persistense;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductJpaEntity, String> {

    Page<ProductJpaEntity> findAll(Specification<ProductJpaEntity> whereClause, Pageable page);

    @Query(value ="select p.id from Product p where p.id in :ids")
    List<String> existsByIds(@Param("ids") List<String> ids);

    List<ProductJpaEntity> findByIdIn(List<String> productIds);
}
