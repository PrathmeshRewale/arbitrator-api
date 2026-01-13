package com.mac.arbitrator.dto.request.update;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateLegalCaseClaimantDocumentRequestDto {
    private String section17DocPath;
    private String statementOfClaimPath;
    private String additionalDocumentPath;
}
