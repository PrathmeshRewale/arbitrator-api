package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.Jurisdiction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JurisdictionRepository extends JpaRepository<Jurisdiction, Long> {
    boolean existsByName(String name);

}