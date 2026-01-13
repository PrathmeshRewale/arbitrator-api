package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.ArbitratorLegalCase;
import com.mac.arbitrator.entity.ArbitratorUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArbitratorLegalCaseRepository extends JpaRepository<ArbitratorLegalCase, Long> {
    List<ArbitratorUser> findByClaimantEmail(String claimantEmail);
    long countByClaimantEmailAndArbitratorId(String claimantEmail, Long id);
    ArbitratorLegalCase findByLegalCaseId(Long id);
    Page<ArbitratorLegalCase> findAllByArbitratorId(Long arbitratorId, Pageable pageable);
}