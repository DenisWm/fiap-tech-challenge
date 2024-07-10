package com.fiap.tech.fiap_tech_challenge.common.application.utils;


import com.fiap.tech.fiap_tech_challenge.common.domain.AggregateRoot;
import com.fiap.tech.fiap_tech_challenge.common.domain.Identifier;
import com.fiap.tech.fiap_tech_challenge.common.domain.exceptions.DomainException;
import com.fiap.tech.fiap_tech_challenge.common.domain.exceptions.NotFoundException;

import java.util.function.Supplier;

public final class IDNotFoundUtils {
    public static Supplier<DomainException> notFound(final Identifier anId, Class<? extends AggregateRoot<?>> anAggregate) {
        return () -> NotFoundException.with(anAggregate, anId);
    }
}
