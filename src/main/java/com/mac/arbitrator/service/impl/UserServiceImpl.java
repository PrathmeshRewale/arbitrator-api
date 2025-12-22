package com.mac.arbitrator.service.impl;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateUserRequest;
import com.mac.arbitrator.dto.request.update.UpdateUserPasswordRequest;
import com.mac.arbitrator.dto.request.update.UpdateUserRequest;
import com.mac.arbitrator.dto.response.UserResponseDto;
import com.mac.arbitrator.entity.Role;
import com.mac.arbitrator.entity.User;
import com.mac.arbitrator.exception.UserNotFoundException;
import com.mac.arbitrator.repository.RoleRepository;
import com.mac.arbitrator.repository.UserRepository;
import com.mac.arbitrator.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> {
                    Role role = roleRepository.findById(user.getRoleId()).orElse(null);
                    return this.toDto(user, role);
                })
                .toList();
    }

    @Override
    public GenericResponseDto createUser(CreateUserRequest createUserRequest) {
        // Check if username already exists
        if (userRepository.existsByUsername(createUserRequest.username())) {
            return new GenericResponseDto("error", "Username already exists");
        }

        // Check if email already exists
        if (userRepository.existsByEmail(createUserRequest.email())) {
            return new GenericResponseDto("error", "Email already exists");
        }

        // Check if phone number already exists
        if (userRepository.existsByPhoneNo(createUserRequest.phoneNo())) {
            return new GenericResponseDto("error", "Phone number already exists");
        }

        // If all checks pass, save the user
        userRepository.saveAndFlush(mapCreateRequestToEntity(createUserRequest));
        return new GenericResponseDto("success", "User created successfully");
    }


    @Override
    public GenericResponseDto updateUserWithUserId(Long userId, UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException(NOT_FOUND,"User not found"));
        mapUpdateRequestToEntity(updateUserRequest,user);
        userRepository.saveAndFlush(user);
        return new GenericResponseDto("success", "User updated successfully");
    }

    @Override
    public GenericResponseDto deleteUserById(Long id) {
        return userRepository.findById(id).map(user -> {
            userRepository.deleteById(id);
            return new GenericResponseDto("success", "User deleted successfully");
        }).orElseThrow(() -> new UserNotFoundException(NOT_FOUND, "User not found"));
    }

    @Override
    public GenericResponseDto updateUserPasswordWithUserId(Long userId, UpdateUserPasswordRequest updateUserPasswordRequest) {
        User user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException(NOT_FOUND,"User not found"));
        user.setPassword(passwordEncoder.encode(updateUserPasswordRequest.password()));
        user.setUpdatedAt(Instant.now());
        user.setUpdatedById(updateUserPasswordRequest.updatedById());
        user.setUpdatedByName(updateUserPasswordRequest.updatedByName());
        userRepository.save(user);
        return new GenericResponseDto("success", "User password updated successfully");
    }


    public User mapCreateRequestToEntity(CreateUserRequest dto) {
        if (dto == null) {
            return null;
        }
        return User.builder()
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .fullName(dto.fullName())
                .email(dto.email())
                .phoneNo(dto.phoneNo())
                .roleId(dto.roleId())
                .createdById(dto.createdById())
                .createdByName(dto.createdByName())
                .createdAt(Instant.now())
                .build();
    }

    public void mapUpdateRequestToEntity(UpdateUserRequest dto,User user) {
        user.setFullName(dto.fullName());
        user.setEmail(dto.email());
        user.setPhoneNo(dto.phoneNo());
        user.setRoleId(dto.roleId());
        user.setUpdatedAt(Instant.now());
        user.setUpdatedById(dto.updatedById());
        user.setUpdatedByName(dto.updatedByName());

    }


    public UserResponseDto toDto(User user, Role role) {
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNo(user.getPhoneNo())
                .roleId(user.getRoleId())
                .roleName(role != null ? role.getName() : null)
                .build();
    }
}
