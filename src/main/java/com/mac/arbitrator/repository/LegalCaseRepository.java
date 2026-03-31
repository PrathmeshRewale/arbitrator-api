package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.LegalCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LegalCaseRepository extends JpaRepository<LegalCase, Long> {
    Page<LegalCase> findByAdmissionFormIdIn(List<Long> admissionIds, PageRequest pageRequest);
    Page<LegalCase> findByIdIn(List<Long> ids, Pageable pageable);
    Optional<LegalCase> findByAdmissionFormId(Long admissionId);
    long count();
    Optional<LegalCase> findById(Long id);

    List<LegalCase> findByAdmissionFormIdIn(List<Long> admissionIds);
}