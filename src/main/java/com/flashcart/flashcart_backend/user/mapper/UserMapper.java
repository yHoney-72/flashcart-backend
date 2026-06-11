package com.flashcart.flashcart_backend.user.mapper;

import com.flashcart.flashcart_backend.user.dto.UserRequestDTO;
import com.flashcart.flashcart_backend.user.dto.UserResponseDTO;
import com.flashcart.flashcart_backend.user.entity.User;


//@Mapper
public interface UserMapper {
    User toEntity(UserRequestDTO userRequestDTO);
    UserResponseDTO toResponse(User user);

}
