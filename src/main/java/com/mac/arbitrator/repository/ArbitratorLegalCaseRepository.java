package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.ArbitratorLegalCase;
import com.mac.arbitrator.entity.ArbitratorUser;
import com.mac.arbitrator.entity.enums.HearingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArbitratorLegalCaseRepository extends JpaRepository<ArbitratorLegalCase, Long> {
    List<ArbitratorUser> findByClaimantEmail(String claimantEmail);
    long countByClaimantEmailAndArbitratorId(String claimantEmail, Long id);
    long countByClaimantEmailAndArbitratorIdAndStatusNot(
            String claimantEmail,
            Long arbitratorId,
            HearingStatus status
    );
    ArbitratorLegalCase findByLegalCaseId(Long id);
    Page<ArbitratorLegalCase> findAllByArbitratorId(Long arbitratorId, Pageable pageable);

    Long countByArbitratorId(Long id);

    List<ArbitratorLegalCase> findByArbitratorId(Long id);
}