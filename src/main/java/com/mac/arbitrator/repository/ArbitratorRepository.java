package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.Arbitrator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArbitratorRepository extends JpaRepository<Arbitrator, Long> {
    List<Arbitrator> findByJurisdictionId(Long jurisdictionId);
    long count();
    List<Arbitrator> findTop10ByOrderByIdDesc();

}