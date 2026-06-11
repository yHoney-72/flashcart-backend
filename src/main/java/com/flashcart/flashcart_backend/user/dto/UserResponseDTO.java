package com.flashcart.flashcart_backend.user.dto;

import com.flashcart.flashcart_backend.enums.user.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDTO {
   private Long id;
   private String name;
   private String email;
   private Role role;
   private LocalDateTime  createdAt;
}
