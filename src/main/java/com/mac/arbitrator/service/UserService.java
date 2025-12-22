package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateUserRequest;
import com.mac.arbitrator.dto.request.update.UpdateUserPasswordRequest;
import com.mac.arbitrator.dto.request.update.UpdateUserRequest;
import com.mac.arbitrator.dto.response.UserResponseDto;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers();
    GenericResponseDto createUser(CreateUserRequest createUserRequest);
    GenericResponseDto updateUserWithUserId(Long userId, UpdateUserRequest updateUserRequest);
    GenericResponseDto deleteUserById(Long id);
    GenericResponseDto updateUserPasswordWithUserId(Long userId, UpdateUserPasswordRequest updateUserPasswordRequest);
}
