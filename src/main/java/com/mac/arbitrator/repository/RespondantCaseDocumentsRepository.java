package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.RespondantCaseDocuments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespondantCaseDocumentsRepository extends JpaRepository<RespondantCaseDocuments,Long> {
    List<RespondantCaseDocuments> findByCaseId(Long caseId);
}
