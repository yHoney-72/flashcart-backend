package com.flashcart.flashcart_backend.user.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRequestDTO {
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;

   
    @NotBlank
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,20}$",
            message = "Password must have uppercase, lowercase, digit, special character, and be 8-20 characters long"
    )
    private String password;


}
