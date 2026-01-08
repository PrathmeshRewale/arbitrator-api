package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Boolean existsByName(String name);
}