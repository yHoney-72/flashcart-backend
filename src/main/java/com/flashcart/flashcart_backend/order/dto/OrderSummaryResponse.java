package com.flashcart.flashcart_backend.order.dto;

import com.flashcart.flashcart_backend.enums.orders.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class OrderSummaryResponse {
    private String trackingId;

    private String storeName;

    private BigDecimal totalAmount;

    private OrderStatus status;

    private LocalDateTime createdAt;
}
