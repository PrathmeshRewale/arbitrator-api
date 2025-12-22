package com.mac.arbitrator.dto.request.update;

import com.mac.arbitrator.entity.enums.AdmissionFormStatus;

public record UpdateCaseStatusRequest(
        Long id,
        AdmissionFormStatus status
){
}
