package com.flashcart.flashcart_backend.exception;

public class VehicleNumberAlreadyExistsException extends RuntimeException {
    public VehicleNumberAlreadyExistsException(String message) {
        super(message);
    }
}
