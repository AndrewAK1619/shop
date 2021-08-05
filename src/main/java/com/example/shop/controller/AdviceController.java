package com.example.shop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j // dodaje możliwość trorzenia logów w klasie
@RestControllerAdvice
public class AdviceController {

    @ResponseStatus(HttpStatus.CONFLICT) // zwraca status dla użytkownika
    // jakbym chciał w kontrolerach zamienić domyślny status na inny
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class) // ta adnotacja przechwytuje exeption'a
    public void handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        log.error("Entity already exist", e);

        // 5 poziomów logowania
        // - info - do informacji
        // - error - do błędów
        // - debug - do debagowania (defualtowo wyłączony) (więcej informacji od info)
        // - trace - bardziej szczegółowy debug (defualtowo wyłączony)
        // - warn - ostrzerzenie

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public void handleEntityNotFoundException(EntityNotFoundException e) {
        log.error("Entity not found", e);
    }

    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Validation failed", e);
    }
}
