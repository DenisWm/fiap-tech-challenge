package com.fiap.tech.common.domain.exceptions;

import com.fiap.tech.common.domain.AggregateRoot;
import com.fiap.tech.common.domain.Identifier;
import com.fiap.tech.common.domain.validation.Error;

import java.util.Collections;
import java.util.List;

public class NotFoundException extends DomainException{

    protected NotFoundException(String aMessage, List<Error> anErrors) {
        super(aMessage, anErrors);
    }

    public static NotFoundException with(
           final Class<? extends AggregateRoot<?>> anAggregate,
           final Identifier id
    ) {
        final var anErrorMessage = "%s with ID %s was not found".formatted(anAggregate.getSimpleName(), id.getValue());
        return new NotFoundException(anErrorMessage, Collections.emptyList());
    }

    public static NotFoundException with(
            final Class<? extends AggregateRoot<?>> anAggregate,
            final String cpf
    ) {
        final var anErrorMessage = "%s with CPF %s was not found".formatted(anAggregate.getSimpleName(),cpf);
        return new NotFoundException(anErrorMessage, Collections.emptyList());
    }

}
