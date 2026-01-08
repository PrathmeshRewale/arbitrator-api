package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.Jurisdiction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JurisdictionRepository extends JpaRepository<Jurisdiction, Long> {
    Boolean existsByName(String name);
}