package com.flashcart.flashcart_backend.driver.dto;


import com.flashcart.flashcart_backend.enums.driver.DriverStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateDriverStatusRequest {
    @NotNull(message = "Driver status is required.")
    private DriverStatus status;

}
