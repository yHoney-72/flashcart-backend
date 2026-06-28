package com.flashcart.flashcart_backend.driver.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateDriverLocationRequest {
    @NotNull(message = "Current latitude is required.")
    private Double currentLatitude;

    @NotNull(message = "Current longitude is required.")
    private Double currentLongitude;
}
