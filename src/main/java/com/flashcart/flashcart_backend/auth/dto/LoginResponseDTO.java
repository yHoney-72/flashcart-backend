package com.flashcart.flashcart_backend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor

@Data
public class LoginResponseDTO {
    private String message;
    private String token;
}
