package com.flashcart.flashcart_backend.user.mapper;

import com.flashcart.flashcart_backend.user.dto.UserRequestDTO;
import com.flashcart.flashcart_backend.user.dto.UserResponseDTO;
import com.flashcart.flashcart_backend.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public User toEntity(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());
        return user;
    }

    @Override
    public UserResponseDTO toResponse(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setRole(user.getRole());
        userResponseDTO.setCreatedAt(user.getCreatedAt());
     return userResponseDTO;
    }
}
