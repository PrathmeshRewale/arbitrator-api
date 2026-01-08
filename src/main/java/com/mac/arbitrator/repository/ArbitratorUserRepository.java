package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.ArbitratorUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArbitratorUserRepository extends JpaRepository<ArbitratorUser, Long> {
}