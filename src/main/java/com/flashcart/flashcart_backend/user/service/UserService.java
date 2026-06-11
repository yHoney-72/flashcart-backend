package com.flashcart.flashcart_backend.user.service;

import com.flashcart.flashcart_backend.enums.user.Login;
import com.flashcart.flashcart_backend.enums.user.Role;
import com.flashcart.flashcart_backend.exception.EmailAlreadyExistsException;
import com.flashcart.flashcart_backend.user.dto.UserRequestDTO;
import com.flashcart.flashcart_backend.user.dto.UserResponseDTO;
import com.flashcart.flashcart_backend.user.entity.User;
import com.flashcart.flashcart_backend.user.mapper.UserMapper;
import com.flashcart.flashcart_backend.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder= passwordEncoder;
        this.userMapper = userMapper;
    }
    public UserResponseDTO registerCustomer(UserRequestDTO userRequestDTO) {

        validateEmailNotAlreadyRegistered(userRequestDTO.getEmail());

        User user = userMapper.toEntity(userRequestDTO);

        user.setRole(Role.CUSTOMER);

        user.setLoginType(Login.EMAIL);
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));

        user.setEmailVerified(false);
        user.setPhoneVerified(false);

        user = userRepository.save(user);

        return userMapper.toResponse(user);
    }
    public UserResponseDTO registerStoreOwner(UserRequestDTO userRequestDTO) {

        validateEmailNotAlreadyRegistered(userRequestDTO.getEmail());

        User user = userMapper.toEntity(userRequestDTO);

        user.setRole(Role.STORE_OWNER);

        user.setLoginType(Login.EMAIL);
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));

        user.setEmailVerified(false);
        user.setPhoneVerified(false);

        user = userRepository.save(user);

        return userMapper.toResponse(user);
    }
private  void validateEmailNotAlreadyRegistered(String email) {
        if(userRepository.existsByEmail(email)){
            throw new EmailAlreadyExistsException("Email already registered");
        }
}

}
