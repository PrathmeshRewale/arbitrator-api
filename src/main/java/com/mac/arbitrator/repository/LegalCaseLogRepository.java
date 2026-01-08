package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.LegalCaseLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LegalCaseLogRepository extends JpaRepository<LegalCaseLog, Long> {
}