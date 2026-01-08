package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.MediationForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediationFormRepository extends JpaRepository<MediationForm, Long> {
}