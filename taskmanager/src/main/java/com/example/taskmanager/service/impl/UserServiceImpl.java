package com.example.taskmanager.service.impl;

import com.example.taskmanager.dto.ApiResponse;
import com.example.taskmanager.dto.UserDTO;
import com.example.taskmanager.exception.NotFoundException;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserRepository;
import com.example.taskmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public ApiResponse<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOS = userRepository.findAll().stream()
                .map(this::mapUserToUserDTO)
                .toList();

        return new ApiResponse<>(200, "Users retrieved successfully", userDTOS);
    }

    @Override
    public ApiResponse<UserDTO> getMyProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new NotFoundException("User not found"));

        UserDTO userDTO = mapUserToUserDTO(user);

        return new ApiResponse<>(200, "profile retrieved", userDTO);
    }

    @Override
    public ApiResponse<UserDTO> updateUserProfile(String email, UserDTO userDTO) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new NotFoundException("User not found"));

        if(userDTO.getEmail() != null) user.setEmail(userDTO.getEmail());

        User updatedUser = userRepository.save(user);

        return new ApiResponse<>(200, "Profile updated successfully", mapUserToUserDTO(updatedUser));
    }

    private UserDTO mapUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());

        return userDTO;
    }
}
