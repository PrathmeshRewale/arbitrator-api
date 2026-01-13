package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.MediationForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediationFormRepository extends JpaRepository<MediationForm, Long> {
    long count();
    List<MediationForm> findTop10ByOrderByIdDesc();
}