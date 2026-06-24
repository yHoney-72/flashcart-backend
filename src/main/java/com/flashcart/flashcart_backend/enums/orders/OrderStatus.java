package com.flashcart.flashcart_backend.enums.orders;


import java.util.EnumSet;

public enum OrderStatus {
    PLACED,
    CONFIRMED,
    PACKING,
    SHIPPED,
    DELIVERED,
    CANCELLED;

    private EnumSet<OrderStatus> allowedNextStatuses;

    static {
        PLACED.allowedNextStatuses = EnumSet.of(CONFIRMED, CANCELLED);
        CONFIRMED.allowedNextStatuses = EnumSet.of(PACKING, CANCELLED);
        PACKING.allowedNextStatuses = EnumSet.of(SHIPPED, CANCELLED);
        SHIPPED.allowedNextStatuses = EnumSet.of(DELIVERED);
        DELIVERED.allowedNextStatuses = EnumSet.noneOf(OrderStatus.class);
        CANCELLED.allowedNextStatuses = EnumSet.noneOf(OrderStatus.class);
    }

    public boolean canTransitionTo(OrderStatus nextStatus) {
        return allowedNextStatuses.contains(nextStatus);
    }
}
