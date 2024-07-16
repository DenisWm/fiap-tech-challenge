package com.fiap.tech.fiap_tech_challenge.common.domain;

public abstract class AggregateRoot<ID extends Identifier> extends Entity<ID> {
    public AggregateRoot(final ID id) {
        super(id);
    }
}
