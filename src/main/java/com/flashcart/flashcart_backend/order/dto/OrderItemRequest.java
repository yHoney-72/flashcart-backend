package com.flashcart.flashcart_backend.order.dto;

import lombok.Data;

@Data
public class OrderItemRequest {
    Long productId;
    Integer quantity;
}
