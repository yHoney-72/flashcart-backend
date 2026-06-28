package com.flashcart.flashcart_backend.driver.dto;

import com.flashcart.flashcart_backend.enums.driver.DriverAvailability;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateDriverAvailabilityRequest {

    @NotNull(message = "Driver availability is required.")
    private DriverAvailability availability;
}
