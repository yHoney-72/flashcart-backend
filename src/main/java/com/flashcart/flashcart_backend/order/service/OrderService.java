package com.flashcart.flashcart_backend.order.service;

import com.flashcart.flashcart_backend.order.dto.*;

import java.util.List;

public interface OrderService {
    CreateOrderResponse createOrder(CreateOrderRequest request);

    List<OrderSummaryResponse> getMyOrders();

    OrderDetailsResponse getOrderDetails(String trackingId);

    void cancelOrder(String trackingId);

    void updateOrderStatus(String trackingId, UpdateOrderStatusRequest request);
}

