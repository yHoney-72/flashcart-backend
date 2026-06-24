package com.flashcart.flashcart_backend.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {

    @NotNull
    private Long storeId;

    @NotEmpty
    @Valid
    private List<OrderItemRequest> items;

    @NotBlank
    private String deliveryName;

    @NotBlank
    @Pattern(regexp = "^[0-9]{10}$",
            message = "Phone number must be 10 digits")
    private String deliveryPhone;

    @NotBlank
    private String addressLine1;

    private String addressLine2;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    @Pattern(regexp = "^[0-9]{6}$",
            message = "Pincode must be 6 digits")
    private String pincode;
}