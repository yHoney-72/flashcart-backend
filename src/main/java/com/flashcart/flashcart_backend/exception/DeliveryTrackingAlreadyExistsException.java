package com.flashcart.flashcart_backend.exception;

public class DeliveryTrackingAlreadyExistsException extends RuntimeException {
    public DeliveryTrackingAlreadyExistsException(String message) {
        super(message);
    }
}
