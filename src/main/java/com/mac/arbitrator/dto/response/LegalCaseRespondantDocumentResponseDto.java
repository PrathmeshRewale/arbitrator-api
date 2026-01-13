package com.mac.arbitrator.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LegalCaseRespondantDocumentResponseDto {
    private Long id;
    private Long legalCaseId;
    private String documentTitle;
    private String documentUrl;
    private Long respondantId;
    private String respondantName;
    private LocalDateTime uploadedAt;
}
