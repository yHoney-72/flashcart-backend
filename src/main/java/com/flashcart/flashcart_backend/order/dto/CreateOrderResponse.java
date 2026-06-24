package com.flashcart.flashcart_backend.order.dto;

import com.flashcart.flashcart_backend.enums.orders.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
@Data
@Builder
public class CreateOrderResponse {

    private String trackingId;
    private OrderStatus status;
    private BigDecimal totalAmount;

}
