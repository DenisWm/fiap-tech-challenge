package com.fiap.tech.fiap_tech_challenge.common.application.utils;


import com.fiap.tech.fiap_tech_challenge.common.domain.AggregateRoot;
import com.fiap.tech.fiap_tech_challenge.common.domain.Identifier;
import com.fiap.tech.fiap_tech_challenge.common.domain.exceptions.DomainException;
import com.fiap.tech.fiap_tech_challenge.common.domain.exceptions.NotFoundException;

import java.util.function.Supplier;

public final class CpfNotFoundUtils {
    public static Supplier<DomainException> notFound(final String cpf, Class<? extends AggregateRoot<?>> anAggregate) {
        return () -> NotFoundException.with(anAggregate, cpf);
    }
}
