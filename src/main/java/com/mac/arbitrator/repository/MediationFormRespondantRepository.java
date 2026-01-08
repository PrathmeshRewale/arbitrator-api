package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.AdmissionFormRespondant;
import com.mac.arbitrator.entity.MediationFormRespondant;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MediationFormRespondantRepository extends JpaRepository<MediationFormRespondant, Long> {
    @Query(value = "SELECT * FROM mediation_form_respondant r WHERE r.admission_form_id = :id", nativeQuery = true)
    List<MediationFormRespondant> findByMediationFormId(long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM mediation_form_respondant r WHERE r.admission_form_id = :id", nativeQuery = true)
    void deletebyMediationFormId(long id);
}