package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select * from user where username = ?1",nativeQuery = true)
    Optional<User> findByUsername(String username);
    User findByUsernameOrEmail(String username, String userEmail);
    Boolean existsByUsername(String name);
    Page<User> findAllByRoleNameNot(String role, Pageable pageable);
    long countByRoleNameNot(String roleName);
    List<User> findTop10ByOrderByIdDesc();
}