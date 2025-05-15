package org.javadev.subscription.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends UserException {
    public UserAlreadyExistsException(String userName) {
        super("User already exists with username: " + userName);
    }
}