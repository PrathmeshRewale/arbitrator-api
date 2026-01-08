package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select * from user where username = ?1",nativeQuery = true)
    Optional<User> findByUsername(String username);

    User findByUsernameOrEmail(String username, String userEmail);
}