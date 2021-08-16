package com.example.shop.controller;

import com.example.shop.model.dto.FieldErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j // dodaje możliwość trorzenia logów w klasie
@RestControllerAdvice
public class AdviceController {

    @ResponseStatus(HttpStatus.CONFLICT) // zwraca status dla użytkownika
    // jakbym chciał w kontrolerach zamienić domyślny status na inny
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class) // ta adnotacja przechwytuje exeption'a
    public void handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        log.error("Entity already exist", e);

        // logi dajemy w cachu i gdzieś gdzie warto by było jak uznamy

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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<FieldErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Validation failed", e);
        return e.getAllErrors().stream()
                .map(err -> {
                    FieldError fieldError = (FieldError) err;
                    return new FieldErrorDto(fieldError.getField(), fieldError.getDefaultMessage());
                })
                .collect(Collectors.toList());
    }
}
