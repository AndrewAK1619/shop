package com.example.shop.exception;

public class ExceededQuantityException extends RuntimeException {

    public ExceededQuantityException(String message) {
        super(message);
    }
}
