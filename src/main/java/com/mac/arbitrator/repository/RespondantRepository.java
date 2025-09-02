package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.Respondant;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RespondantRepository extends JpaRepository<Respondant, Long> {

    @Query(value = "SELECT * FROM respondant r WHERE r.admission_form_id = :id", nativeQuery = true)
    List<Respondant> findByAdmissionFormId(long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM respondant r WHERE r.admission_form_id = :id", nativeQuery = true)
    void deletebyAdmissionFormId(long id);
}
