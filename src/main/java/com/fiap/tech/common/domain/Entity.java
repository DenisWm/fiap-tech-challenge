package com.fiap.tech.common.domain;


import com.fiap.tech.common.domain.validation.ValidationHandler;
import com.fiap.tech.event.DomainEvent;
import com.fiap.tech.event.DomainEventPublisher;

import java.util.*;

public abstract class Entity<ID extends Identifier> {

    protected final ID id;

    private final List<DomainEvent> domainEvents;

    public Entity(final ID id, final List<DomainEvent> domainEvents) {
        Objects.requireNonNull(id, "'id' should not be null");
        this.id = id;
        this.domainEvents = new ArrayList<>(domainEvents == null ? Collections.emptyList() : domainEvents);
    }

    public Entity(final ID id) {
        Objects.requireNonNull(id, "'id' should not be null");
        this.id = id;
        this.domainEvents = new ArrayList<>();
    }
    public abstract void validate(ValidationHandler handler);
    public ID getId() {
        return id;
    }

    public List<DomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    public void publishDomainEvent(final DomainEventPublisher<DomainEvent> publisher){
        if(publisher == null){
            return;
        }

        getDomainEvents().forEach(publisher::publishEvent);

        this.domainEvents.clear();
    }

    public void registerEvent(final DomainEvent event){
        if (event != null){
            this.domainEvents.add(event);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Entity<?> entity = (Entity<?>) o;
        return Objects.equals(getId(), entity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
