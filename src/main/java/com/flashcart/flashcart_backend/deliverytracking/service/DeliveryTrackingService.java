package com.flashcart.flashcart_backend.deliverytracking.service;

import com.flashcart.flashcart_backend.deliverytracking.dto.DeliveryTrackingRequest;
import com.flashcart.flashcart_backend.deliverytracking.dto.DeliveryTrackingResponse;

public interface DeliveryTrackingService {
    DeliveryTrackingResponse createDeliveryTracking(Long orderId , Long driverId, DeliveryTrackingRequest request);
    DeliveryTrackingResponse getDeliveryTrackingById(Long orderId);
    DeliveryTrackingResponse updateDeliveryTracking(Long orderId, DeliveryTrackingRequest request);
}
