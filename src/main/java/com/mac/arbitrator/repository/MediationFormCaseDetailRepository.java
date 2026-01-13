package com.mac.arbitrator.repository;

import com.mac.arbitrator.entity.MediationFormCaseDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediationFormCaseDetailRepository extends JpaRepository<MediationFormCaseDetail, Long> {
    MediationFormCaseDetail findByMediationFormId(Long mediationId);
}