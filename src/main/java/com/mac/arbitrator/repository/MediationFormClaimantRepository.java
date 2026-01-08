package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.AdmissionFormClaimant;
import com.mac.arbitrator.entity.MediationFormClaimant;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MediationFormClaimantRepository extends JpaRepository<MediationFormClaimant, Long> {

    @Query(value = "SELECT * FROM mediation_form_claimant WHERE admission_form_id = :id", nativeQuery = true)
    List<MediationFormClaimant> findByMediationFormId(long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM mediation_form_claimant WHERE admission_form_id = :id", nativeQuery = true)
    void deletebyMediationFormId(long id);
}