package com.mac.arbitrator.dto.request.update;

import com.mac.arbitrator.entity.enums.AdmissionFormStatus;

public record UpdateCaseMiniRequest(
        String caseNo,
        AdmissionFormStatus status,
        String rejectionReason,
        Long updatedById,
        String updatedByName
) {
}
