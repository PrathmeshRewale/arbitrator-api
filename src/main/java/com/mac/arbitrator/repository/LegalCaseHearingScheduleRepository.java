package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.LegalCaseHearingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface LegalCaseHearingScheduleRepository extends JpaRepository<LegalCaseHearingSchedule, Long> {
    Optional<LegalCaseHearingSchedule> findByLegalCaseId(Long caseId);
    @Modifying
    @Transactional
    void deleteByLegalCaseId(Long legalCaseId);
    List<LegalCaseHearingSchedule> findAll();


}