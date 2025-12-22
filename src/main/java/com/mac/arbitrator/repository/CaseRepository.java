package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.Case;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CaseRepository extends JpaRepository<Case,Long> {
    Optional<Case> findByAdmissionFormId(Long admissionId);
    boolean existsByAdmissionFormId(Long admissionFormId);
}
