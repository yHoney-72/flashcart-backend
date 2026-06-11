package com.flashcart.flashcart_backend.auth.service;

import com.flashcart.flashcart_backend.auth.dto.LoginRequestDTO;
import com.flashcart.flashcart_backend.auth.dto.LoginResponseDTO;
import com.flashcart.flashcart_backend.exception.InvalidCredentialsException;
import com.flashcart.flashcart_backend.security.JwtService;
import com.flashcart.flashcart_backend.user.entity.User;
import com.flashcart.flashcart_backend.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
private  final UserRepository userRepository;
private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtService jwtService) {
    this.userRepository = userRepository;

    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtService = jwtService;
    }
public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
    User user = userRepository.findByEmail(loginRequestDTO.getEmail());
    if(user == null){
        throw new InvalidCredentialsException("Invalid credentials");
    }
    boolean matches = bCryptPasswordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword());
    if(!matches){
        throw new InvalidCredentialsException("Invalid credentials");
    }
    String token = jwtService.generateToken(user);
return new LoginResponseDTO("Login Success",token);
}
}
