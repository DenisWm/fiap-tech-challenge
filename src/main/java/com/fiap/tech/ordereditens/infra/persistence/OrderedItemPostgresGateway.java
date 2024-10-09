package com.fiap.tech.ordereditens.infra.persistence;

import com.fiap.tech.ordereditens.domain.OrderedItem;
import com.fiap.tech.ordereditens.domain.OrderedItemGateway;
import com.fiap.tech.ordereditens.domain.OrderedItemID;
import com.fiap.tech.product.domain.ProductID;
import com.fiap.tech.product.infra.persistense.ProductRepository;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderedItemPostgresGateway implements OrderedItemGateway {
    private final OrderedItemRepository repository;
    private final ProductRepository productRepository;

    public OrderedItemPostgresGateway(OrderedItemRepository repository, ProductRepository productRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
    }

    @Override
    public OrderedItem create(OrderedItem orderedItem) {
        return repository.save(OrderedItemJpaEntity.from(orderedItem)).toAggregate();
    }

    @Override
    public OrderedItem update(OrderedItem orderedItem) {
        return repository.save(OrderedItemJpaEntity.from(orderedItem)).toAggregate();
    }

    @Override
    public Optional<OrderedItem> findById(OrderedItemID anId) {
        return repository.findById(anId.getValue()).map(OrderedItemJpaEntity::toAggregate);
    }

    @Override
    public List<OrderedItem> findByIds(List<String> orderedItemIds) {
        return repository.findAllById(orderedItemIds).stream().map(OrderedItemJpaEntity::toAggregate).toList();
    }
}
