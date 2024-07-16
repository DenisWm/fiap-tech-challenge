package com.fiap.tech.fiap_tech_challenge.common.domain.exceptions;

import com.fiap.tech.fiap_tech_challenge.common.domain.validation.handler.Notification;

public class NotificationException extends DomainException {

    public NotificationException(final String message, final Notification notification) {
        super(message, notification.getErrors());
    }
}
