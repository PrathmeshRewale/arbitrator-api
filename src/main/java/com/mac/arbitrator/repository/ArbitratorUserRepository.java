package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.ArbitratorUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArbitratorUserRepository extends JpaRepository<ArbitratorUser, Long> {
    Optional<ArbitratorUser> findByArbitratorId(Long arbitratorId);
    Optional<ArbitratorUser> findByUserId(Long userId);
}