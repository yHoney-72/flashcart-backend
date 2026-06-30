package com.flashcart.flashcart_backend.auth.service;

import com.flashcart.flashcart_backend.auth.dto.LoginRequestDTO;
import com.flashcart.flashcart_backend.auth.dto.LoginResponseDTO;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
}
