package com.flashcart.flashcart_backend.exception;

public class LicenseNumberAlreadyExistsException extends RuntimeException {
    public LicenseNumberAlreadyExistsException(String message) {
        super(message);
    }
}
