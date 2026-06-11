package com.flashcart.flashcart_backend.auth.controller;
import com.flashcart.flashcart_backend.auth.dto.LoginRequestDTO;
import com.flashcart.flashcart_backend.auth.dto.LoginResponseDTO;
import com.flashcart.flashcart_backend.auth.service.AuthService;
import com.flashcart.flashcart_backend.user.dto.UserRequestDTO;
import com.flashcart.flashcart_backend.user.dto.UserResponseDTO;
import com.flashcart.flashcart_backend.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;
    private  final AuthService authService;
    public AuthController(UserService userService, AuthService authService){
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register/customer")
    public UserResponseDTO registerCustomer(
            @Valid @RequestBody UserRequestDTO userRequestDTO){
        return userService.registerCustomer(userRequestDTO);
    }
    @PostMapping("/register/store-owner")
    public UserResponseDTO registerStoreOwner(
            @Valid @RequestBody UserRequestDTO userRequestDTO){
        return userService.registerStoreOwner(userRequestDTO);
    }
    @PostMapping("/login")
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
     return   authService.login(loginRequestDTO);

    }
}
