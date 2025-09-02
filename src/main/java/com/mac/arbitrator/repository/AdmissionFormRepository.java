/**
 * -----------------------------------------------------------------------------
 * Author      : Prathmesh Rewale (https://github.com/prathmeshrewale)
 * Date        : 2025-09-01
 * Company     : Webzworld
 * File        : AdmissionFormRepository.java
 * Purpose     : Creates AdmissionForm Repository for the application, It's used for CRUD operations on AdmissionForm Entity
 * -----------------------------------------------------------------------------
 */

package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.AdmissionForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdmissionFormRepository extends JpaRepository<AdmissionForm, Long> {

    @Query(value = "SELECT * FROM admission_form a WHERE a.id = :id", nativeQuery = true)
    Optional<AdmissionForm> findById(Long id);



}
