package org.javadev.subscription.api.error.handler;

import lombok.extern.slf4j.Slf4j;
import org.javadev.subscription.exception.SubscriptionAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class SubscriptionExceptionHandler {
    @ExceptionHandler(SubscriptionAlreadyExistsException.class)
    public ResponseEntity<String> handle(SubscriptionAlreadyExistsException ex) {
        log.error("Subscription already exists!", ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}