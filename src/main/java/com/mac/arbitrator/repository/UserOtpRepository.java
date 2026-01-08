package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.UserOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserOtpRepository extends JpaRepository<UserOtp, Long> {
    @Transactional
    @Modifying
    void deleteByUserId(Long userId);
    Optional<UserOtp> findByUserId(Long id);
}