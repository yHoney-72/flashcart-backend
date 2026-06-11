package com.flashcart.flashcart_backend.store.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class StoreRequestDTO {

    @NotBlank
    private String storeName;

    @NotBlank
    private String description;

    @NotBlank
    private String addressLine1;

    private String addressLine2;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    private String pincode;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @NotNull
    @Positive
    private Integer deliveryRadiusKm;
}