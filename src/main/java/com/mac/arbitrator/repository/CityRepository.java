package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface CityRepository extends JpaRepository<City, Long> {
    Boolean existsByName(String name);

    @Modifying
    @Transactional
    void deleteAllByStateId(Long countryId);
}