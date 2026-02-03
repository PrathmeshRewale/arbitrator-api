package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.LegalCaseRespondantDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LegalCaseRespondantDocumentRepository extends JpaRepository<LegalCaseRespondantDocument, Long> {
    List<LegalCaseRespondantDocument> findAllByLegalCaseId(Long caseId);
}