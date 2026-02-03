package com.mac.arbitrator.service.impl;

import com.mac.arbitrator.dto.GenericResponseDto;
import com.mac.arbitrator.dto.request.create.CreateUserRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateUserPasswordRequestDto;
import com.mac.arbitrator.dto.request.update.UpdateUserRequestDto;
import com.mac.arbitrator.dto.response.UserResponseDto;
import com.mac.arbitrator.entity.User;
import com.mac.arbitrator.repository.UserRepository;
import com.mac.arbitrator.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()->new IllegalArgumentException("User with username -> " + username + " not found"));
    }

    @Override
    public Boolean checkAvailability(String name) {
        return userRepository.existsByUsername(name);
    }

    @Override
    public GenericResponseDto create(CreateUserRequestDto req) {

        User user = new User();
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setFullName(req.getFullName());
        user.setUsername(req.getUsername());
        user.setPhoneNo(req.getPhoneNo());
        user.setRoleId(req.getRoleId());
        user.setRoleName(req.getRoleName());
        user.setAlternativeEmail(req.getAlternativeEmail());
        user.setAlternativePhoneNo(req.getAlternativePhoneNo());

        userRepository.save(user);

        return new GenericResponseDto("success","record added successfully");
    }

    @Override
    public GenericResponseDto update(Long id, UpdateUserRequestDto req) {

        User user = userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("User with id -> " + id + " not found"));
        user.setEmail(req.getEmail());
        user.setFullName(req.getFullName());
        user.setUsername(req.getUsername());
        user.setPhoneNo(req.getPhoneNo());
        user.setRoleId(req.getRoleId());
        user.setRoleName(req.getRoleName());
        user.setAlternativeEmail(req.getAlternativeEmail());
        user.setAlternativePhoneNo(req.getAlternativePhoneNo());

        userRepository.save(user);

        return new GenericResponseDto("success","record updated successfully");
    }

    @Override
    public GenericResponseDto delete(Long id) {
        userRepository.deleteById(id);
        return new GenericResponseDto("success","record deleted successfully");
    }

    @Override
    public Page<UserResponseDto> findAll(Pageable pageable) {
        return userRepository.findAllByRoleNameNot("ARBITRATOR",pageable).map(this::mapToDto);
    }

    @Override
    public GenericResponseDto updateUserPassword(UpdateUserPasswordRequestDto updateUserPasswordRequestDto) {
        User user = userRepository.findById(updateUserPasswordRequestDto.getUserId()).orElseThrow(()->new IllegalArgumentException("User with id -> " + updateUserPasswordRequestDto.getUserId() + " not found"));
        user.setPassword(passwordEncoder.encode(updateUserPasswordRequestDto.getNewPassword()));

        userRepository.save(user);

        return new GenericResponseDto("success","record updated successfully");
    }

    @Override
    public User getUserByUserEmail(String userEmail) {
        return userRepository.findByUsernameOrEmail("",userEmail);
    }

    private UserResponseDto mapToDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                user.getPhoneNo(),
                user.getAlternativePhoneNo(),
                user.getAlternativeEmail(),
                user.getRoleId(),
                user.getRoleName()
        );
    }

}
