package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.CaseLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CaseLogRepository extends JpaRepository<CaseLog,Long> {
    List<CaseLog> findByCaseId(Long caseId);
}
