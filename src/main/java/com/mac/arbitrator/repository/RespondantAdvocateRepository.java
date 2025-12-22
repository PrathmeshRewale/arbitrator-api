package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.RespondantAdvocate;
import com.mac.arbitrator.entity.RespondantAdvocateEmbeddable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RespondantAdvocateRepository extends JpaRepository<RespondantAdvocate, RespondantAdvocateEmbeddable> {
    Optional<RespondantAdvocate> findById_CaseId(Long caseId);
}
