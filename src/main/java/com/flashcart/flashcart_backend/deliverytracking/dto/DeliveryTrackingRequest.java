package com.flashcart.flashcart_backend.deliverytracking.dto;

import com.flashcart.flashcart_backend.enums.driver.DeliveryStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryTrackingRequest {
    @NotNull(message = "Driver latitude is required.")
    private Double driverLatitude;

    @NotNull(message = "Driver longitude is required.")
    private Double driverLongitude;

    @NotNull(message = "Delivery status is required.")
    private DeliveryStatus deliveryStatus;
}
