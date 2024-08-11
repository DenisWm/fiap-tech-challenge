package com.fiap.tech.common.domain.validation.handler;



import com.fiap.tech.common.domain.validation.Error;
import com.fiap.tech.common.domain.validation.ValidationHandler;
import com.fiap.tech.common.domain.exceptions.DomainException;

import java.util.ArrayList;
import java.util.List;

public class Notification implements ValidationHandler {

    private final List<Error> errors;
    @Override
    public Notification append(Error anError) {
        this.errors.add(anError);
        return this;
    }

    private Notification(final List<Error> errors) {
        this.errors = errors;
    }
    public static Notification create(final Throwable t) {
        return create(new Error(t.getMessage()));
    }
    public static Notification create() {
        return new Notification(new ArrayList<>());
    }
    public static Notification create(Error anError) {
        return new Notification(new ArrayList<>()).append(anError);
    }
    @Override
    public ValidationHandler append(final ValidationHandler aHandler) {
        this.errors.addAll(aHandler.getErrors());
        return this;
    }

    @Override
    public <T> T validate(final Validation<T> aValidation) {
        try {
            return aValidation.validate();
        } catch (DomainException ex) {
            this.errors.addAll(ex.getErrors());
        } catch (Throwable t) {
            this.errors.add(new Error(t.getMessage()));
        }
        return null;
    }

    @Override
    public List<Error> getErrors() {
        return this.errors;
    }
}
