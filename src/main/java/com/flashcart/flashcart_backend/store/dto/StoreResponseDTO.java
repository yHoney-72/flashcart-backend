package com.flashcart.flashcart_backend.store.dto;

import com.flashcart.flashcart_backend.enums.store.StoreStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoreResponseDTO {

    private Long id;

    private String storeName;

    private String description;

    private String city;

    private String state;

    private Double latitude;

    private Double longitude;

    private Integer deliveryRadiusKm;

    private String ownerName;

    private StoreStatus status;

    private LocalDateTime createdAt;
}