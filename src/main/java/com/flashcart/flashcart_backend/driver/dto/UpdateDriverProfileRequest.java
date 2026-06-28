package com.flashcart.flashcart_backend.driver.dto;

import com.flashcart.flashcart_backend.enums.driver.VehicleType;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateDriverProfileRequest {

    @NotNull(message = "Vehicle type is required.")
    private VehicleType vehicleType;

    @NotBlank(message = "Vehicle number is required.")
    @Pattern(
            regexp = "^[A-Z]{2}[0-9]{1,2}[A-Z]{1,3}[0-9]{4}$",
            message = "Invalid vehicle number format."
    )
    private String vehicleNumber;

    @NotNull(message = "Current latitude is required.")
    @DecimalMin(value = "-90.0", message = "Latitude must be greater than or equal to -90.")
    @DecimalMax(value = "90.0", message = "Latitude must be less than or equal to 90.")
    private Double currentLatitude;

    @NotNull(message = "Current longitude is required.")
    @DecimalMin(value = "-180.0", message = "Longitude must be greater than or equal to -180.")
    @DecimalMax(value = "180.0", message = "Longitude must be less than or equal to 180.")
    private Double currentLongitude;
}