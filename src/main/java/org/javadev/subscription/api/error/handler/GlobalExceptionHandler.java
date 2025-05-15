package org.javadev.subscription.api.error.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // @ExceptionHandler перехватывает тип исключений IllegalArgumentException и возвращаем неверный запрос,
    // т.к. эта ошибка связана с клиентом.
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handle(IllegalArgumentException ex) {
        log.error("Illegal argument exception!", ex);
        return ResponseEntity.badRequest().body("Illegal argument exception! " + ex.getMessage());
    }

    // @ExceptionHandler перехватывает общий тип исключений RuntimeException и возвращаем неверный запрос,
    // т.к. эта ошибка связана с клиентом.
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handle(RuntimeException ex) {
        log.error("Service runtime exception!", ex);
        return ResponseEntity.badRequest().body("Service runtime exception! " + ex.getMessage());
    }

    // @ExceptionHandler перехватывает общий тип исключений Exception и возвращаем код внутренней ошибки сервера,
    // т.к. эта ошибка чаще всего связана с сервером.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handle(Exception ex) {
        log.error("Internal server error!", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("A server error occurred!");
    }

}