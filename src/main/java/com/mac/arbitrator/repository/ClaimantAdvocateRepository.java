package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.ClaimantAdvocate;
import com.mac.arbitrator.entity.ClaimantAdvocateEmbeddable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClaimantAdvocateRepository extends JpaRepository<ClaimantAdvocate, ClaimantAdvocateEmbeddable> {
    Optional<ClaimantAdvocate> findById_CaseId(Long caseId);
}
