package com.flashcart.flashcart_backend.driver.dto;

import com.flashcart.flashcart_backend.enums.driver.VehicleType;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DriverRegistrationRequest {

    @NotBlank(message = "Full name is required")
    @Size(min = 3, max = 100, message = "Full name must be between 3 and 100 characters")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$",message = "Phone number must be a valid 10-digit Indian mobile number")
    private String phoneNumber;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String password;

    @NotBlank(message = "License number is required")
    @Size(min = 10, max = 25, message = "Invalid license number")
    private String licenseNumber;

    @NotNull(message = "Vehicle type is required")
    private VehicleType vehicleType;

    @NotBlank(message = "Vehicle number is required")
    @Pattern(regexp = "^[A-Z]{2}[0-9]{1,2}[A-Z]{1,3}[0-9]{4}$",message = "Invalid vehicle registration number")
    private String vehicleNumber;
}
