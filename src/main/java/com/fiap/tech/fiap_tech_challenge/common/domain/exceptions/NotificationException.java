package com.fiap.tech.fiap_tech_challenge.common.domain.exceptions;

import com.fiap.tech.fiap_tech_challenge.common.domain.validation.handler.Notification;
import com.fiap.tech.fiap_tech_challenge.common.domain.validation.handler.ThrowsValidationHandler;

public class NotificationException extends DomainException {

    public NotificationException(final String message, final Notification notification) {
        super(message, notification.getErrors());
    }
    public NotificationException(final String message, final ThrowsValidationHandler notification) {
        super(message, notification.getErrors());
    }
}
