package com.mac.arbitrator.service.impl;

import com.mac.arbitrator.entity.User;
import com.mac.arbitrator.repository.UserRepository;
import com.mac.arbitrator.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()->new IllegalArgumentException("User with username -> " + username + " not found"));
    }
}
