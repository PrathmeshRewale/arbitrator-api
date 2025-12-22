package com.mac.arbitrator.dto.request.update;

import com.mac.arbitrator.entity.enums.AdmissionFormStatus;

public record UpdateCaseRequest(
        String caseNo,
        Long admissionFormId,
        String section17DocPath,
        String statementOfClaimPath,
        AdmissionFormStatus status,
        Long updatedById,
        String updatedByName
) {
}
