package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.AdmissionFormUser;
import com.mac.arbitrator.entity.enums.UserCaseType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdmissionFormUserRepository extends JpaRepository<AdmissionFormUser, Long> {
    List<AdmissionFormUser> findByUserId(Long id);
    List<AdmissionFormUser> findByAdmissionIdAndUserCaseType(Long admissionId, UserCaseType userCaseType);

    List<AdmissionFormUser> findByUserIdAndUserCaseType(Long userId, UserCaseType userCaseType);
}