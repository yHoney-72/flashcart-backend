package com.flashcart.flashcart_backend.auth.service;

import com.flashcart.flashcart_backend.auth.dto.LoginRequestDTO;
import com.flashcart.flashcart_backend.auth.dto.LoginResponseDTO;
import com.flashcart.flashcart_backend.exception.InvalidCredentialsException;
import com.flashcart.flashcart_backend.security.JwtService;
import com.flashcart.flashcart_backend.user.entity.User;
import com.flashcart.flashcart_backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByEmail(loginRequestDTO.getEmail()).orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));
        boolean matches = passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword());
        if (!matches) {
            throw new InvalidCredentialsException("Invalid credentials");
        }
        String token = jwtService.generateToken(user);
        return new LoginResponseDTO("Login Success", token);
    }
}

