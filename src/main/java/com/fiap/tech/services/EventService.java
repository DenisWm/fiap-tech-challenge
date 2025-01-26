package com.fiap.tech.services;

import com.fiap.tech.event.DomainEvent;

public interface EventService {
    void send(Object event);
}
