package com.fiap.tech.payment.infra.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentJpaEntity, String> {

    Page<PaymentJpaEntity> findAll(Specification<PaymentJpaEntity> whereClause, Pageable page);

}
