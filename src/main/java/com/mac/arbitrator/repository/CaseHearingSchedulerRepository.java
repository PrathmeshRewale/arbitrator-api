package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.CaseHearingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CaseHearingSchedulerRepository extends JpaRepository<CaseHearingSchedule, Long> {
    @Query("SELECT ch FROM CaseHearingSchedule ch WHERE ch.caseId = :caseId")
    CaseHearingSchedule findByCaseId(Long caseId);

    // Fetch the latest hearing schedule entry for a given caseId
    Optional<CaseHearingSchedule> findTopByCaseIdOrderByNextHearingDesc(Long caseId);
}