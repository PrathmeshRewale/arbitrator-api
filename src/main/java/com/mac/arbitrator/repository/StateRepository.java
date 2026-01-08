package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StateRepository extends JpaRepository<State, Long> {
    Boolean existsByName(String name);
    @Modifying
    @Transactional
    void deleteAllByCountryId(Long countryId);

    List<State> findAllByCountryId(Long countryId);
}