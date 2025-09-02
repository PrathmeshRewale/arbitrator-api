package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.Claimant;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClaimantRepository extends JpaRepository<Claimant, Long> {

    @Query(value = "SELECT * FROM claimant WHERE admission_form_id = :id", nativeQuery = true)
    List<Claimant> findByAdmissionFormId(long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM claimant WHERE admission_form_id = :id", nativeQuery = true)
    void deletebyAdmissionFormId(long id);
}
