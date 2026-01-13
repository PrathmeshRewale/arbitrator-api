package com.mac.arbitrator.dto.request.update;

import com.mac.arbitrator.entity.enums.CaseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateLegalCaseStatusRequestDto {
    private Long caseId;
    private CaseStatus status;
    private String rejectionReason;
}
