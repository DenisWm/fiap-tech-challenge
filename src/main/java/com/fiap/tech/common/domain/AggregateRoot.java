package com.fiap.tech.common.domain;

import com.fiap.tech.event.DomainEvent;

import java.util.Collections;
import java.util.List;

public abstract class AggregateRoot<ID extends Identifier> extends Entity<ID> {
    public AggregateRoot(final ID id) {
        this(id, Collections.emptyList());
    }

    public AggregateRoot(final ID id, List<DomainEvent> domainEvents) {
        super(id, domainEvents);
    }
}
