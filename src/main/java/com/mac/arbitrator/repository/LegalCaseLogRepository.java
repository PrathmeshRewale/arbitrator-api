package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.LegalCaseLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LegalCaseLogRepository extends JpaRepository<LegalCaseLog, Long> {
    List<LegalCaseLog> findAllByLegalCaseId(Long caseId);
}