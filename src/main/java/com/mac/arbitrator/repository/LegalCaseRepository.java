package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.LegalCase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LegalCaseRepository extends JpaRepository<LegalCase, Long> {
}