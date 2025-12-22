package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.UserAdmissionForm;
import com.mac.arbitrator.entity.UserAdmissionFormEmbeddable;
import com.mac.arbitrator.entity.enums.UserCaseType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAdmissionFormRepository extends JpaRepository<UserAdmissionForm, UserAdmissionFormEmbeddable> {
    List<UserAdmissionForm> findAllByUserAdmissionFormEmbeddableUserid(Long userid);
    boolean existsByUserAdmissionFormEmbeddable_AdmissionFormId(Long admissionFormId);
    List<UserAdmissionForm>
    findAllByUserAdmissionFormEmbeddable_UseridAndUserAdmissionFormEmbeddable_UserCaseType(
            Long userid,
            UserCaseType userCaseType
    );
    List<UserAdmissionForm> findAllByUserAdmissionFormEmbeddable_UserCaseType(UserCaseType userCaseType);
}
