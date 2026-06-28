package com.flashcart.flashcart_backend.driver.dto;

import com.flashcart.flashcart_backend.enums.driver.DriverAvailability;
import com.flashcart.flashcart_backend.enums.driver.DriverStatus;
import com.flashcart.flashcart_backend.enums.driver.VehicleType;
import lombok.Data;

@Data
public class DriverProfileResponse {

    private Long id;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String licenseNumber;

    private VehicleType vehicleType;

    private String vehicleNumber;

    private DriverStatus status;

    private DriverAvailability availability;

    private Double rating;

    private Integer ratingCount;

    private Integer totalDeliveries;

    private Boolean verified;

    private Double currentLatitude;

    private Double currentLongitude;
}