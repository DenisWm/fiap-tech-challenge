package com.fiap.tech.fiap_tech_challenge.common.domain.exceptions;

public class NoStacktraceRuntimeException extends RuntimeException {
    public NoStacktraceRuntimeException(final String message) {
        this(message, null);
    }

    public NoStacktraceRuntimeException(final String message, Throwable cause) {
        super(message, cause, true, false);
    }
}
