package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {
  boolean existsByUserIdAndOtp(Long userid,Integer otp);
  Otp findByUserId(Long userId);
  @Modifying
  @Transactional
  void deleteAllByUserId(Long id);
  @Modifying
  @Transactional
  void deleteByUserId(Long userId);
}