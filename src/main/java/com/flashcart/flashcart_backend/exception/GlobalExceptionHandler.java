package com.flashcart.flashcart_backend.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.access.AccessDeniedException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice   //->Spring, is class ko automatically register kar lo
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return Map.of("message", ex.getMessage());
    }
    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        return Map.of("message", ex.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
    for(FieldError error : ex.getBindingResult().getFieldErrors()){
        errors.put(error.getField(),error.getDefaultMessage());
    }
    return errors;
    //->MethodArgumentNotValidException Spring validation fail hone par khud throw karta hai
        //->FieldError ek validation error slip/object ,(store):field name + error message)
        //->BindingResult sari FieldError slips ka box
        //->default msg bi spring khud bna deta
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleDataIntegrityViolationException() {
        return Map.of("message", "Database constraint violation");
    }
    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handleInvalidCredentialsException() {
        return Map.of("message", "Invalid credentials");
    }


    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, String> handleAccessDeniedException(AccessDeniedException ex) {

        return Map.of("message", ex.getMessage());
    }
@ExceptionHandler(OutOfStockException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleOutOfStockException(OutOfStockException ex) {
        return Map.of("message", ex.getMessage());
}
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleIllegalStateException(
            IllegalStateException ex) {

        return Map.of("message", ex.getMessage());
    }
    @ExceptionHandler(PhoneNumberAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handlePhoneNumberAlreadyExistsException(PhoneNumberAlreadyExistsException ex) {
        return Map.of("message", ex.getMessage());
    }
    @ExceptionHandler(LicenseNumberAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleLicenseNumberAlreadyExistsException(LicenseNumberAlreadyExistsException ex) {
        return Map.of("message", ex.getMessage());
    }
    @ExceptionHandler(VehicleNumberAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleVehicleNumberAlreadyExistsException(VehicleNumberAlreadyExistsException ex) {
        return Map.of("message", ex.getMessage());
    }

}
