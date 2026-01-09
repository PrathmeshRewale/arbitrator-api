package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.ArbitratorUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArbitratorUserRepository extends JpaRepository<ArbitratorUser, Long> {
}