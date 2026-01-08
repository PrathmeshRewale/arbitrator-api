package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.PartyType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyTypeRepository extends JpaRepository<PartyType, Long> {
    Boolean existsByName(String name);
}