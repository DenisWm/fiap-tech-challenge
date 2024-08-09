package com.fiap.tech.order.infra.persistence;

import com.fiap.tech.common.domain.pagination.Pagination;
import com.fiap.tech.common.infra.utils.SpecificationUtils;
import com.fiap.tech.order.domain.Order;
import com.fiap.tech.order.domain.OrderGateway;
import com.fiap.tech.order.domain.OrderID;
import com.fiap.tech.product.application.retrieve.list.OrderSearchQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class OrderPostgresGateway implements OrderGateway {

    private final OrderRepository orderRepository;
    public OrderPostgresGateway(final OrderRepository orderRepository) {
        this.orderRepository = Objects.requireNonNull(orderRepository);
    }

    @Override
    public Order create(final Order order) {
        return orderRepository
                .save(OrderJpaEntity.from(order))
                .toAggregate();
    }

    @Override
    public Pagination<Order> findAll(OrderSearchQuery aQuery) {
        final var page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        final var where = Optional.ofNullable(aQuery.clientId())
                .filter(str -> !str.isBlank())
                .map(this::assembleSpecification)
                .orElse(null);

        final var result = orderRepository.findAll(Specification.where(where), page);

        return new Pagination<>(
               result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.map(OrderJpaEntity::toAggregate).toList()
        );
    }

    @Override
    public Optional<Order> findById(OrderID anId) {
        return this.orderRepository.findById(anId.getValue()).map(OrderJpaEntity::toAggregate);
    }

    private Specification<OrderJpaEntity> assembleSpecification(final String client) {
        return SpecificationUtils.equal("clientId", client);
    }
}
