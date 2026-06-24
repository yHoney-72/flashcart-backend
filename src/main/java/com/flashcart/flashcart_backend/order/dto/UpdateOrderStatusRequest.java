package com.flashcart.flashcart_backend.order.dto;

import com.flashcart.flashcart_backend.enums.orders.OrderStatus;
import lombok.Data;

@Data

public class UpdateOrderStatusRequest {
    private OrderStatus status;
}
