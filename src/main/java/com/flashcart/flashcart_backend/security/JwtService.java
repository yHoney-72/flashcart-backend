package com.flashcart.flashcart_backend.security;

import com.flashcart.flashcart_backend.user.entity.User;

public interface JwtService {
    String generateToken(User user);

    String extractUsername(String token);

    boolean isTokenValid(String token, User user);
}
