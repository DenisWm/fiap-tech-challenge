package com.fiap.tech.fiap_tech_challenge.order.infra.persistence;

import com.fiap.tech.fiap_tech_challenge.product.infra.persistense.ProductJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderJpaEntity, String> {

    Page<OrderJpaEntity> findAll(Specification<OrderJpaEntity> whereClause, Pageable page);

}
