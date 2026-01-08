package com.mac.arbitrator.service;

import com.mac.arbitrator.entity.User;

public interface UserService {
    User findByUsername(String username);
}
