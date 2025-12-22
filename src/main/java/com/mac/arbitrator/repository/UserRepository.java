package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhoneNo(String phoneNo);
    Optional<User> findById(Long userid);
    Optional<User> findByEmail(String email);
    boolean existsByBarRegistrationNumber(String number);
    User findByUsernameOrEmail(String Username,String email);
}
