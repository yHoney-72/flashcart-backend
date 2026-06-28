package com.flashcart.flashcart_backend.driver.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VerifyDriverRequest {
    @NotNull(message = "Verification status is required.")
    private Boolean verified;
}
