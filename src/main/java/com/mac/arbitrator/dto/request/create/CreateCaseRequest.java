package com.mac.arbitrator.dto.request.create;

import com.mac.arbitrator.entity.enums.AdmissionFormStatus;

public record CreateCaseRequest(
        String caseNo,
        Long admissionFormId,
        String section17DocPath,
        String statementOfClaimPath,
        AdmissionFormStatus status,
        Long createdById,
        String createdByName
) {
}
