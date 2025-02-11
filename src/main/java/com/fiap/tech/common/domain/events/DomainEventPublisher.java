package com.fiap.tech.common.domain.events;

@FunctionalInterface
public interface DomainEventPublisher {

    void publish(DomainEvent event);
}
