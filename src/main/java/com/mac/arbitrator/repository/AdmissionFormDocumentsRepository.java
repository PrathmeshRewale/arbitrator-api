package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.AdmissionFormDocuments;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AdmissionFormDocumentsRepository extends JpaRepository<AdmissionFormDocuments, Long> {

    @Query(value = "SELECT * FROM admission_form_documents a WHERE a.admission_form_id = :id", nativeQuery = true)
    AdmissionFormDocuments findByAdmissionFormId(long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM admission_form_documents a WHERE a.admission_form_id = :id", nativeQuery = true)
    void deletebyAdmissionFormId(long id);
}
