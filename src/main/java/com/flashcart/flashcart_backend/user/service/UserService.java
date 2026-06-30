package com.flashcart.flashcart_backend.user.service;

import com.flashcart.flashcart_backend.user.dto.UserRequestDTO;
import com.flashcart.flashcart_backend.user.dto.UserResponseDTO;

public interface UserService {
    UserResponseDTO registerCustomer(UserRequestDTO userRequestDTO);

    UserResponseDTO registerStoreOwner(UserRequestDTO userRequestDTO);
}
