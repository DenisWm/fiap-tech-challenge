package com.fiap.tech.order.infra.persistence;

import com.fiap.tech.common.domain.pagination.Pagination;
import com.fiap.tech.common.infra.utils.SpecificationUtils;
import com.fiap.tech.order.domain.Order;
import com.fiap.tech.order.domain.OrderGateway;
import com.fiap.tech.order.domain.OrderID;
import com.fiap.tech.order.domain.OrderStatus;
import com.fiap.tech.product.application.retrieve.list.OrderSearchQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
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
                .map(spec -> spec.and(excludeCompleted()))
                .orElse(excludeCompleted());

        final var result = orderRepository.findAll(Specification.where(where), page);

        List<Order> sortedOrders = result.stream()
                .map(OrderJpaEntity::toAggregate)
                .sorted(this::statusPriorityComparator)
                .toList();

        return new Pagination<>(
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                sortedOrders
        );
    }

    private Specification<OrderJpaEntity> excludeCompleted() {
        return SpecificationUtils.notEqual("status", OrderStatus.COMPLETED.getValue());
    }

    private int statusPriorityComparator(Order o1, Order o2) {
        List<OrderStatus> priority = List.of(OrderStatus.READY, OrderStatus.IN_PREPARATION, OrderStatus.RECEIVED);
        return Integer.compare(priority.indexOf(o1.getStatus()), priority.indexOf(o2.getStatus()));
    }


    @Override
    public Optional<Order> findById(OrderID anId) {
        return this.orderRepository.findById(anId.getValue()).map(OrderJpaEntity::toAggregate);
    }

    @Override
    public Order update(Order anOrder) {
        return this.orderRepository.save(OrderJpaEntity.from(anOrder)).toAggregate();
    }

    private Specification<OrderJpaEntity> assembleSpecification(final String client) {
        return SpecificationUtils.equal("clientId", client);
    }
}
