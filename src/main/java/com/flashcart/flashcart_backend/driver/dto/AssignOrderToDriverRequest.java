package com.flashcart.flashcart_backend.driver.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignOrderToDriverRequest {
    @NotNull(message = "Order id is required.")
    private Long orderId;
}
