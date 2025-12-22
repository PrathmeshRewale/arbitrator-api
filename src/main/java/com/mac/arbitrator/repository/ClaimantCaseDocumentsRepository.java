package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.ClaimantCaseDocuments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClaimantCaseDocumentsRepository extends JpaRepository<ClaimantCaseDocuments, Long> {
    List<ClaimantCaseDocuments> findByCaseId(Long caseId);
}
