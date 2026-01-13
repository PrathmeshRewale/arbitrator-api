package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.LegalCaseClaimantDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LegalCaseClaimantDocumentRepository extends JpaRepository<LegalCaseClaimantDocument, Long> {
    List<LegalCaseClaimantDocument> findAllByLegalCaseId(Long caseId);
}