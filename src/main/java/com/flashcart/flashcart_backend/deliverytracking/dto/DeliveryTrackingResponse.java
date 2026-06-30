package com.flashcart.flashcart_backend.deliverytracking.dto;

import com.flashcart.flashcart_backend.enums.driver.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryTrackingResponse {

    private Long orderId;

    private String trackingId;

    private String driverName;

    private String driverPhone;

    private Double driverLatitude;

    private Double driverLongitude;

    private Double remainingDistanceKm;

    private Integer etaMinutes;

    private DeliveryStatus deliveryStatus;

    private LocalDateTime updatedAt;
}
