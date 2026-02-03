package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.AdmissionFormDocuments;
import com.mac.arbitrator.entity.MediationFormDocuments;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MediationFormDocumentsRepository extends JpaRepository<MediationFormDocuments, Long> {
    @Query(value = "SELECT * FROM mediation_form_documents a WHERE a.mediation_form_id = :id", nativeQuery = true)
    MediationFormDocuments findByMediationFormId(long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM mediation_form_documents a WHERE a.mediation_form_id = :id", nativeQuery = true)
    void deletebyMediationFormId(long id);
}