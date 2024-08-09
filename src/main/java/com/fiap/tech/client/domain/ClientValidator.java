package com.fiap.tech.client.domain;

import com.fiap.tech.common.domain.validation.Error;
import com.fiap.tech.common.domain.validation.ValidationHandler;
import com.fiap.tech.common.domain.validation.Validator;

public class ClientValidator extends Validator {

    public static final int NAME_MAX_LENGTH = 255;
    public static final int NAME_MIN_LENGTH = 1;
    public static final String NAME_SHOULD_NOT_BE_NULL = "'name' should not be null";
    public static final String NAME_SHOULD_NOT_BE_EMPTY = "'name' should not be empty";
    public static final String NAME_MUST_BE_BETWEEN_1_AND_255_CHARACTERS = "'name' must be between 1 and 255 characters";
    private final Client client;

    protected ClientValidator(ValidationHandler aHandler, Client client) {
        super(aHandler);
        this.client = client;
    }

    @Override
    public void validate() {
        checkNameConstraints();

    }

    private void checkNameConstraints() {
        final var name = this.client.getName();
        if(name == null) {
            getHandler().append(new Error(NAME_SHOULD_NOT_BE_NULL));
            return;
        }
        if(name.isBlank()) {
            getHandler().append(new Error(NAME_SHOULD_NOT_BE_EMPTY));
            return;
        }
        final int nameLength = name.trim().length();
        if(nameLength < NAME_MIN_LENGTH || nameLength > NAME_MAX_LENGTH) {
            getHandler().append(new Error(NAME_MUST_BE_BETWEEN_1_AND_255_CHARACTERS));
        }
    }
}
