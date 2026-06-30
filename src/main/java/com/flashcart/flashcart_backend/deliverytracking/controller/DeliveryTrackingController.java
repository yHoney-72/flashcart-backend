package com.flashcart.flashcart_backend.deliverytracking.controller;

import com.flashcart.flashcart_backend.deliverytracking.dto.DeliveryTrackingRequest;
import com.flashcart.flashcart_backend.deliverytracking.dto.DeliveryTrackingResponse;
import com.flashcart.flashcart_backend.deliverytracking.service.DeliveryTrackingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/delivery-tracking")
public class DeliveryTrackingController {

    private final DeliveryTrackingService deliveryTrackingService;

@PostMapping("/orders/{orderId}/drivers/{driverId}")
@PreAuthorize("hasAnyRole('ADMIN', 'STORE_OWNER')")
     public ResponseEntity<DeliveryTrackingResponse> createDeliveryTracking(@PathVariable Long orderId, @PathVariable Long driverId, @Valid @RequestBody DeliveryTrackingRequest request) {
       DeliveryTrackingResponse response = deliveryTrackingService.createDeliveryTracking(orderId, driverId, request);
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
}

    @GetMapping("/orders/{orderId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STORE_OWNER', 'DELIVERY_AGENT', 'CUSTOMER')")
    public ResponseEntity<DeliveryTrackingResponse> getDeliveryTrackingById(@PathVariable Long orderId) {
        DeliveryTrackingResponse response = deliveryTrackingService.getDeliveryTrackingById(orderId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/orders/{orderId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DELIVERY_AGENT')")
    public ResponseEntity<DeliveryTrackingResponse> updateDeliveryTracking(@PathVariable Long orderId, @Valid @RequestBody DeliveryTrackingRequest request) {
        DeliveryTrackingResponse response = deliveryTrackingService.updateDeliveryTracking(orderId, request);
        return ResponseEntity.ok(response);
    }
}
