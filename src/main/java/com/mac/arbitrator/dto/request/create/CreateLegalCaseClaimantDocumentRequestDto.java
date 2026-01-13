package com.mac.arbitrator.dto.request.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateLegalCaseClaimantDocumentRequestDto {
    private Long legalCaseId;
    private String documentTitle;
    private String documentUrl;
    private Long claimantId;
    private String claimantName;
}
