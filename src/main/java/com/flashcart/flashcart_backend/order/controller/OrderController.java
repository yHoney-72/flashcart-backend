package com.flashcart.flashcart_backend.order.controller;

import com.flashcart.flashcart_backend.order.dto.*;
import com.flashcart.flashcart_backend.order.entity.Order;
import com.flashcart.flashcart_backend.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.createOrder(request));
    }

    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderSummaryResponse>> getMyOrders() {
        return ResponseEntity.ok(orderService.getMyOrders());
    }

    @GetMapping("/{trackingId}")
    public ResponseEntity<OrderDetailsResponse> getOrderDetails(@PathVariable String trackingId) {
        return ResponseEntity.ok(orderService.getOrderDetails(trackingId));
    }

    @PatchMapping("/{trackingId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable String trackingId) {
        orderService.cancelOrder(trackingId);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('STORE_OWNER')")
    @PatchMapping("/{trackingId}/status")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable String trackingId , @RequestBody UpdateOrderStatusRequest request){
        orderService.updateOrderStatus(trackingId , request);
        return ResponseEntity.noContent().build();
    }
}
