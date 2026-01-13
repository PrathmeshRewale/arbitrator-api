package com.mac.arbitrator.dto.request.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateLegalCaseRespondantDocumentRequestDto {
    private Long legalCaseId;
    private String documentTitle;
    private String documentUrl;
    private Long respondantId;
    private String respondantName;
}
