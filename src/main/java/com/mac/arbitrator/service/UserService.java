package com.mac.arbitrator.service;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateUserRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateUserPasswordRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateUserRequestDto;
import com.mac.arbitrator.dto.response.UserResponseDto;
import com.mac.arbitrator.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User findByUsername(String username);
    Boolean checkAvailability(String name);
    GenericResponseDto create(CreateUserRequestDto createUserRequestDto);
    GenericResponseDto update(Long id, UpdateUserRequestDto updateUserRequestDto);
    GenericResponseDto delete(Long id);
    Page<UserResponseDto> findAll(Pageable pageable);
    GenericResponseDto updateUserPassword(UpdateUserPasswordRequestDto updateUserPasswordRequestDto);
    User getUserByUserEmail(String userEmail);
}
