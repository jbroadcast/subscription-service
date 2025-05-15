package org.javadev.subscription.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SubscriptionAlreadyExistsException extends SubscriptionException {
    public SubscriptionAlreadyExistsException(String message) {
        super(message);
    }
}